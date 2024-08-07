package com.sat.board.command.application

import com.sat.board.query.ArticleQueryService
import com.sat.common.domain.RedisCacheName
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.redis.core.DefaultTypedTuple
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class ArticleOfViewsCacheInitializer(
    private val articleQueryService: ArticleQueryService,
    private val redisTemplate: RedisTemplate<String, Any>
): InitializingBean {
    override fun afterPropertiesSet() {
        val articlesOfViews = articleQueryService.getArticlesOfViews()
        val articleViewsZSetOps = redisTemplate.opsForZSet()

        if (articlesOfViews.isEmpty()) {
            val tupleSet = articlesOfViews.map {
                DefaultTypedTuple(it.articleId as Any, it.views.toDouble())
            }.toSet()
            articleViewsZSetOps.add(RedisCacheName.ARTICLE_RANKING.key, tupleSet)
        }
    }
}
