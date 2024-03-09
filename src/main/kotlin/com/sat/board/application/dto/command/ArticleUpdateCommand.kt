package com.sat.board.application.dto.command

import jakarta.validation.constraints.NotBlank

data class ArticleUpdateCommand(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
    val categoryId: Long,
) {
}
