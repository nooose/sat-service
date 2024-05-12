package com.sat.board.application.command.dto

import jakarta.validation.constraints.NotBlank

data class CommentUpdateCommand(
    @field:NotBlank
    val content: String,
)
