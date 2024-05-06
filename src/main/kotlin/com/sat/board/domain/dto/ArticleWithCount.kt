package com.sat.board.domain.dto

data class ArticleWithCount(
    val id: Long,
    val title: String,
    val category: String,
    val commentCount: Long,
    val likeCount: Long,
)
