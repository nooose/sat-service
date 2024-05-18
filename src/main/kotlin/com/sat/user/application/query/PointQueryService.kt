package com.sat.user.application.query

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

    fun getPoints(memberId: Long): List<MyPointQuery> {
        return pointRepository.findAllByMemberId(memberId)
            .map { MyPointQuery.from(it) }
    }
}
