package com.sat.board.application.query.dto

import com.sat.board.domain.Article
import java.time.LocalDateTime

data class ArticleSimpleQuery(
    val id: Long,
    val title: String,
    val createdDateTime: LocalDateTime,
) {

    companion object {
        fun from(entity: Article): ArticleSimpleQuery {
            return ArticleSimpleQuery(
                id = entity.id!!,
                title = entity.title,
                createdDateTime = entity.createdDateTime!!,
            )
        }
    }
}
