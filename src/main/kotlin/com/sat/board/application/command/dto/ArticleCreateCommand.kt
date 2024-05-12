package com.sat.board.application.command.dto

import jakarta.validation.constraints.NotBlank

data class ArticleCreateCommand(
    @field:NotBlank(message = "제목을 입력해 주세요.")
    val title: String,
    @field:NotBlank
    val content: String,
    val categoryId: Long,
)
