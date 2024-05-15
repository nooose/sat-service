package com.sat.board.application.query.dto

import java.time.LocalDateTime

data class CommentWithArticle(
    val id: Long,
    var content: String,
    var parentId: Long,
    val isDeleted: Boolean,
    val articleId: Long,
    val articleTitle: String,
    val createdAt: LocalDateTime,
)
