package com.sat.board.query

import com.fasterxml.jackson.annotation.JsonInclude
import com.sat.board.command.domain.article.Category


@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CategoryQuery(
    val id: Long,
    val name: String,
    val children: MutableList<CategoryQuery> = mutableListOf(),
    val parentId: Long? = null,
) {

    fun hasParent(): Boolean {
        return parentId != null
    }

    companion object {
        fun from(entity: Category): CategoryQuery {
            return CategoryQuery(
                id = entity.id,
                parentId = entity.parentId,
                name = entity.name.value
            )
        }
    }
}
