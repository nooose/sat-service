package com.sat.board.command.domain.article

data class ArticleCreateEvent(
    val memberId: Long,
    val articleId: Long,
)
