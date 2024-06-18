package com.sat.board.command.application

import jakarta.validation.constraints.NotBlank

data class CommentCreateCommand(
    @field:NotBlank(message = "내용을 입력해 주세요")
    val content: String,
    val parentId: Long? = null,
)

data class CommentUpdateCommand(
    @field:NotBlank
    val content: String,
)
