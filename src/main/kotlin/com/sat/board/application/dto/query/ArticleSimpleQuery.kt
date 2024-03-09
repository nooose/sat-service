package com.sat.board.application.dto.query

import com.sat.board.domain.Article

data class ArticleSimpleQuery(
    val id: Long,
    val title: String,
    val category: String,
) {
    companion object {
        fun from(entity: Article): ArticleSimpleQuery {
            return ArticleSimpleQuery(
                id = entity.id!!,
                title = entity.title,
                category = entity.category.name.value,
            )
        }
    }
}
