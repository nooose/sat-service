package com.sat.board.domain.dto

import com.sat.board.domain.Category

data class ArticleDto(
    val title: String,
    val content: String,
    val category: Category,
)
