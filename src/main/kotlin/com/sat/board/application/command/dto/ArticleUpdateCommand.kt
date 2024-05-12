package com.sat.board.application.command.dto

import jakarta.validation.constraints.NotBlank

data class ArticleUpdateCommand(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
)
