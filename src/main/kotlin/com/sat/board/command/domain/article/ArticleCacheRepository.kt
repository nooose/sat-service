package com.sat.board.command.domain.article

interface ArticleCacheRepository {
    fun increase(articleId: Long)
    fun getViews(articleId: Long): Long
    fun getAllArticleViews(): Map<Long, Long>
}
