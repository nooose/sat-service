package com.sat.board.query

import com.sat.common.OnceWriteProperty
import java.time.LocalDateTime

data class ArticleQuery(
    val id: Long,
    val title: String,
    val content: String,
    val category: String,
    val createdBy: Long,
    val createdByName: String,
) {
    var hasLike: Boolean by OnceWriteProperty(false)
    var views: Long = 0
}

data class ArticleWithCountQuery(
    val id: Long,
    val title: String,
    val category: String,
    val commentCount: Long,
    val likeCount: Long,
    val createdDateTime: LocalDateTime,
)

