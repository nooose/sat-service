package com.sat.board.domain.event

data class CommentNotification(
    val title: String,
    val articleId: Long,
)
