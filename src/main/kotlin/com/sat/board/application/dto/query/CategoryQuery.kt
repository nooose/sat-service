package com.sat.board.application.dto.query

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.sat.board.domain.Category

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CategoryQuery(
    val id: Long,
    val name: String,
    val children: MutableList<CategoryQuery> = mutableListOf(),
    @JsonIgnore
    val parentId: Long? = null,
) {

    fun hasParent(): Boolean {
        return parentId != null
    }

    companion object {
        fun from(entity: Category): CategoryQuery {
            return CategoryQuery(
                id = entity.id!!,
                parentId = entity.parentId,
                name = entity.name
            )
        }
    }
}
