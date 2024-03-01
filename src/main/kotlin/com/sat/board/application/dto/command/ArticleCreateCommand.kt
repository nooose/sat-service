package com.sat.board.application.dto.command

data class ArticleCreateCommand(
    val title: String,
    val content: String,
    val categoryId: Long,
) {
}
