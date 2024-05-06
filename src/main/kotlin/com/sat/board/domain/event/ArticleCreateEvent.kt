package com.sat.board.domain.event

data class ArticleCreateEvent(
    val memberId: Long,
    val articleId: Long,
)
