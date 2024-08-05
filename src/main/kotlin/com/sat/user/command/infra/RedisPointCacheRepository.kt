package com.sat.user.command.infra

import com.sat.common.domain.RedisCacheName
import com.sat.user.command.domain.point.PointCacheRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisPointCacheRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
) : PointCacheRepository {
    override fun increase(memberId: Long, point: Int) {
        val valueOperations = redisTemplate.opsForZSet()
        valueOperations.incrementScore(RedisCacheName.POINT_RANKING.key, memberId, point.toDouble())
    }
}
