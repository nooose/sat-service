package com.sat.board.command.infra

import com.sat.board.command.domain.article.ArticleCacheRepository
import com.sat.common.domain.RedisCacheName
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisArticleCacheRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
): ArticleCacheRepository {
    override fun increase(articleId: Long) {
        val valueOperations = redisTemplate.opsForZSet()
        valueOperations.incrementScore(RedisCacheName.ARTICLE_RANKING.key, articleId, 1.0)
    }

    override fun getViews(articleId: Long): Long {
        val valueOperations = redisTemplate.opsForZSet()
        return valueOperations.score(RedisCacheName.ARTICLE_RANKING.key, articleId)!!.toLong()
    }

    override fun getAllArticleViews(): Map<Long, Long> {
        val valueOperations = redisTemplate.opsForZSet()
        val tuples = valueOperations.rangeWithScores(RedisCacheName.ARTICLE_RANKING.key, 0, -1)

        return tuples?.associate { tuple ->
            (tuple.value as Int).toLong() to tuple.score!!.toLong()
        } ?: emptyMap()
    }
}
