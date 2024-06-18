package com.sat.user.query

import com.sat.common.config.jpa.limit
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.user.command.domain.point.Point
import com.sat.user.command.domain.point.PointRepository
import com.sat.user.query.dto.MyPointQuery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PointQueryService(
    private val pointRepository: PointRepository,
) {

    fun getTotalPoint(memberId: Long): Int {
        return pointRepository.findAll {
            select(
                sum(Point::point)
            ).from(
                entity(Point::class)
            ).where(
                path(Point::memberId).equal(memberId)
            )
        }.firstOrNull()?.toInt() ?: 0
    }

    fun getPoints(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<MyPointQuery>> {
        val points = pointRepository.findAll {
            selectNew<MyPointQuery>(
                path(Point::id),
                path(Point::point),
                path(Point::type),
                path(Point::createdDateTime),
            ).from(
                entity(Point::class)
            ).whereAnd(
                cursorRequest.id?.let { path(Point::id).lessThan(it) },
                path(Point::memberId).equal(memberId),
            ).orderBy(
                path(Point::id).desc(),
            ).limit(cursorRequest.size)
        }.filterNotNull()
        return PageCursor(cursorRequest.next(getNextId(points)), points)
    }

    private fun getNextId(points: List<MyPointQuery>): Long {
        if (points.isEmpty()) {
            return 0
        }
        return points.minOf { it.id }
    }
}
