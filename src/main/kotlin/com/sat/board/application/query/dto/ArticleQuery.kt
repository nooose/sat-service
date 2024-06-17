package com.sat.board.application.query.dto

import com.sat.board.domain.Article

data class ArticleQuery(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val hasLike: Boolean,
    val createdBy: Long,
    val createdName: String,
) {
    companion object {
        fun from(entity: Article, memberName: String, hasLike: Boolean): ArticleQuery {
            return ArticleQuery(
                id = entity.id!!,
                title = entity.title,
                content = entity.content,
                category = entity.category.name.value,
                hasLike = hasLike,
                createdBy = entity.createdBy!!,
                createdName = memberName,
            )
        }
    }
}
