package com.sat.board.command.application

import jakarta.validation.constraints.NotBlank


data class CategoryCreateCommand(
    @field:NotBlank
    val name: String,
    val parentId: Long? = null,
)

data class CategoryUpdateCommand(
    @field:NotBlank
    val name: String,
    val parentId: Long?,
)
