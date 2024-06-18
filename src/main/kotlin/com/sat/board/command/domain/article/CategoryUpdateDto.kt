package com.sat.board.command.domain.article

data class CategoryUpdateDto(
    val name: CategoryName,
    val parentId: Long?,
)
