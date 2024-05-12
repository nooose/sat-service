package com.sat.user.domain.port.repository

interface PointRepositoryCustom {
    fun getTotalPoint(memberId: Long): Int
}
