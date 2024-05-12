package com.sat.board.application.command.dto

import jakarta.validation.constraints.NotBlank

data class CategoryCreateCommand(
    @field:NotBlank
    val name: String,
    val parentId: Long? = null,
)
