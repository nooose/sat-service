package com.sat.user.domain.port

interface PointRepositoryCustom {
    fun getTotalPoint(memberId: Long): Int
}
