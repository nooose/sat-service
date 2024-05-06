package com.sat.board.application.dto.query

import com.sat.board.domain.Article

data class ArticleQuery(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val hasLike: Boolean,
) {
    companion object {
        fun from(entity: Article, hasLike: Boolean): ArticleQuery {
            return ArticleQuery(
                id = entity.id!!,
                title = entity.title,
                content = entity.content,
                category = entity.category.name.value,
                hasLike = hasLike,
            )
        }
    }
}
