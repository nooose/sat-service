package com.sat.user.infrastructure.repository

import com.querydsl.jpa.impl.JPAQueryFactory
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
}
