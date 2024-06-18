package com.sat.board.command.application

import jakarta.validation.constraints.NotBlank

data class ArticleCreateCommand(
    @field:NotBlank(message = "제목을 입력해 주세요.")
    val title: String,
    @field:NotBlank
    val content: String,
    val categoryId: Long,
)

data class ArticleUpdateCommand(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
)
