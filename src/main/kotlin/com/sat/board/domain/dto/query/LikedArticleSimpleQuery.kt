package com.sat.board.domain.dto.query

import com.sat.board.domain.Article
import java.time.LocalDateTime

data class LikedArticleSimpleQuery(
    val articleId: Long,
    val title: String,
    val createdDateTime: LocalDateTime,
) {

    companion object {
        fun from(entity: Article): LikedArticleSimpleQuery {
            return LikedArticleSimpleQuery(
                articleId = entity.id!!,
                title = entity.title,
                createdDateTime = entity.createdDateTime!!
            )
        }
    }
}
