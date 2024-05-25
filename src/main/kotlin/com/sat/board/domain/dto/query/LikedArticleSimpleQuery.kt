package com.sat.board.domain.dto.query

import java.time.LocalDateTime

data class LikedArticleSimpleQuery(
    val id: Long,
    val articleId: Long,
    val articleTitle: String,
    val createdDateTime: LocalDateTime,
)
