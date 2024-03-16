package com.sat.board.application.dto.command

import jakarta.validation.constraints.NotBlank

data class CommentUpdateCommand(
    @field:NotBlank
    val content: String,
)
