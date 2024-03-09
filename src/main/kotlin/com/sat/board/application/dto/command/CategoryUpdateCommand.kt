package com.sat.board.application.dto.command

import jakarta.validation.constraints.NotBlank

data class CategoryUpdateCommand(
    @field:NotBlank
    val name: String,
    val parentId: Long?,
)
