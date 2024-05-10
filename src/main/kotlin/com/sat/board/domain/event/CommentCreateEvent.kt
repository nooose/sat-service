package com.sat.board.domain.event

data class CommentCreateEvent(
    val articleId: Long,
    val commentId: Long,
    val commentOwnerId: Long,
)
