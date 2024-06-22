package com.sat.board.query

import java.time.LocalDateTime

data class ArticleQuery(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val createdBy: Long,
    val createdByName: String,
) {
    var hasLike: Boolean = false
}

data class ArticleWithCountQuery(
    val id: Long,
    val title: String,
    val category: String,
    val commentCount: Long,
    val likeCount: Long,
    val createdDateTime: LocalDateTime,
)

