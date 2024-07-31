package com.sat.user.command.application

import com.sat.common.domain.RedisCacheName
import com.sat.user.query.PointQueryService
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class PointCacheService(
    private val pointQueryService: PointQueryService,
    private val redisTemplate: RedisTemplate<String, Any>,
): InitializingBean {
    override fun afterPropertiesSet() {
        val totalPointsOfMembers = pointQueryService.getTotalPointsOfMembers()
        val pointRankingZSetOps = redisTemplate.opsForZSet()
        totalPointsOfMembers.forEach {
            pointRankingZSetOps.add(RedisCacheName.RANKING.key, it.memberId, it.point.toDouble())
        }
    }
}
