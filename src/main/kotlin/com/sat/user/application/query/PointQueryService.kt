package com.sat.user.application.query

import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.user.application.query.dto.MyPointQuery
import com.sat.user.domain.port.repository.PointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PointQueryService(
    private val pointRepository: PointRepository,
) {
    fun getTotalPoint(memberId: Long): Int {
        return pointRepository.getTotalPoint(memberId)
    }

    fun getPoints(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<MyPointQuery>> {
        return pointRepository.findByCursor(memberId, cursorRequest)
    }
}
