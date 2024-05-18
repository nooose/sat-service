package com.sat.board.application.query.dto

import java.time.LocalDateTime

data class CommentWithArticle(
    val id: Long,
    var content: String,
    val articleId: Long,
    val articleTitle: String,
    val createdDateTime: LocalDateTime,
)
