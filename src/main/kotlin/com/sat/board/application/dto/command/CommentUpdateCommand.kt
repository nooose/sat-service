package com.sat.board.application.dto.command

import jakarta.validation.constraints.NotBlank

data class CommentUpdateCommand(
    @NotBlank
    val content: String
)
