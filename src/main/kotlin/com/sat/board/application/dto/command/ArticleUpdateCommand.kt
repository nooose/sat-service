package com.sat.board.application.dto.command

data class ArticleUpdateCommand(
    val title: String,
    val content: String,
    val categoryId: Long,
) {
}
