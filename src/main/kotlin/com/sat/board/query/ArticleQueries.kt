package com.sat.board.query

import com.sat.board.command.domain.article.Article
import java.time.LocalDateTime

data class ArticleQuery(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val hasLike: Boolean,
    val createdBy: Long,
    val createdByName: String,
) {
    companion object {
        fun from(entity: Article, memberName: String, hasLike: Boolean): ArticleQuery {
            return ArticleQuery(
                id = entity.id,
                title = entity.title,
                content = entity.content,
                category = entity.category.name.value,
                hasLike = hasLike,
                createdBy = entity.createdBy!!,
                createdByName = memberName,
            )
        }
    }
}

data class ArticleWithCount(
    val id: Long,
    val title: String,
    val category: String,
    val commentCount: Long,
    val likeCount: Long,
    val createdDateTime: LocalDateTime,
)

