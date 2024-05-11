package com.sat.board.application.dto.query

import com.sat.board.domain.Article

data class ArticleQuery(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val hasLike: Boolean,
    val createdBy: Long,
    // TODO: 게시글 작성자 정보를 보여줘야 한다.
) {
    companion object {
        fun from(entity: Article, hasLike: Boolean): ArticleQuery {
            return ArticleQuery(
                id = entity.id!!,
                title = entity.title,
                content = entity.content,
                category = entity.category.name.value,
                hasLike = hasLike,
                createdBy = entity.createdBy!!,
            )
        }
    }
}
