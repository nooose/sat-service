package com.sat.board.application.dto.command

import jakarta.validation.constraints.NotBlank

data class CommentCreateCommand(
    @field:NotBlank
    val content: String,
    val parentId: Long? = null,
) {

}
