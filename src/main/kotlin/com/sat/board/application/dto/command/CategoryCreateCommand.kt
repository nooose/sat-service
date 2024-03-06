package com.sat.board.application.dto.command

data class CategoryCreateCommand(
    val name: String,
    val parentId: Long? = null,
) {
}
