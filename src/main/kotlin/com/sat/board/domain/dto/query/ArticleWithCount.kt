package com.sat.board.domain.dto.query

import java.time.LocalDateTime

data class ArticleWithCount(
    val id: Long,
    val title: String,
    val category: String,
    val commentCount: Long,
    val likeCount: Long,
    val createdDateTime: LocalDateTime,
)
