package com.sat.user.command.application

import com.sat.common.domain.RedisCacheName
import com.sat.user.query.PointQueryService
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.redis.core.DefaultTypedTuple
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class PointCacheInitializer(
    private val pointQueryService: PointQueryService,
    private val redisTemplate: RedisTemplate<String, Any>,
): InitializingBean {
    override fun afterPropertiesSet() {
        val totalPointsOfMembers = pointQueryService.getTotalPointsOfMembers()
        val pointRankingZSetOps = redisTemplate.opsForZSet()

        val tupleSet = totalPointsOfMembers.map {
            DefaultTypedTuple(it.memberId as Any, it.point.toDouble())
        }.toSet()
        pointRankingZSetOps.add(RedisCacheName.POINT_RANKING.key, tupleSet)
    }
}
