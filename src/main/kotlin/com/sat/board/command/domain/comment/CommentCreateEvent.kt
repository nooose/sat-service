package com.sat.board.command.domain.comment

data class CommentCreateEvent(
    val articleId: Long,
    val commentId: Long,
    val commentOwnerId: Long,
)
