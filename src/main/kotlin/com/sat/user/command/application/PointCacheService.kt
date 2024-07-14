package com.sat.user.command.application

import com.sat.user.query.PointQueryService
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class PointCacheService(
    private val pointQueryService: PointQueryService,
    private val redisTemplate: RedisTemplate<String, Any>
): InitializingBean {
    override fun afterPropertiesSet() {
        val totalPointsOfMembers = pointQueryService.getTotalPointsOfMembers()
        val valueOperations = redisTemplate.opsForValue()
        totalPointsOfMembers.forEach {
            it -> valueOperations[it.memberId.toString()] = it.point
        }
    }
}
