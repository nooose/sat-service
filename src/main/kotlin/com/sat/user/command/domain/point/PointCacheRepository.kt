package com.sat.user.command.domain.point

interface PointCacheRepository {

    fun increase(memberId: Long, point: Int)
}
