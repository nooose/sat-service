package com.sat.board.command.domain.article

data class CategoryDto(
    val name: CategoryName,
    val parentId: Long?,
)
