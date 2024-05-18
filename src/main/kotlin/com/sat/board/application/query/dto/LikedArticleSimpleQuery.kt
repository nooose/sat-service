package com.sat.board.application.query.dto

import com.sat.board.domain.Article

data class LikedArticleSimpleQuery(
    val articleId: Long,
    val title: String,
) {

    companion object {
        fun from(entity: Article): LikedArticleSimpleQuery {
            return LikedArticleSimpleQuery(
                articleId = entity.id!!,
                title = entity.title,
            )
        }
    }
}
