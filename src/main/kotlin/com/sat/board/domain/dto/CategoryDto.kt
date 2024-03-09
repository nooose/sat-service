package com.sat.board.domain.dto

import com.sat.board.domain.CategoryName

data class CategoryDto(
    val name: CategoryName,
    val parentId: Long?,
)
