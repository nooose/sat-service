package com.sat.user.infrastructure.repository

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.user.application.query.dto.MyPointQuery
import com.sat.user.domain.Point
import com.sat.user.domain.QPoint.point1
import com.sat.user.domain.port.repository.PointRepositoryCustom
import org.springframework.stereotype.Component

@Component
class PointRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : PointRepositoryCustom {

    override fun getTotalPoint(memberId: Long): Int {
        return queryFactory.select(point1.point.sum())
            .from(point1)
            .where(point1.memberId.eq(memberId))
            .fetchOne() ?: 0
    }

    override fun findByCursor(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<MyPointQuery>> {
        val points = queryFactory.selectFrom(point1)
            .where(
                lessThanId(cursorRequest.id),
                point1.memberId.eq(memberId),
            )
            .orderBy(point1.id.desc())
            .limit(cursorRequest.size)
            .fetch()

        return PageCursor(cursorRequest.next(getNextId(points)), points.map { MyPointQuery.from(it) })
    }

    private fun lessThanId(id: Long?): BooleanExpression? {
        return id?.let { point1.id.lt(it) }
    }

    private fun getNextId(points: List<Point>): Long {
        if (points.isEmpty()) {
            return 0
        }
        return points.minOf { it.id!! }
    }

}
