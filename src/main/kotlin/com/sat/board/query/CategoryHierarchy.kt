package com.sat.board.query

import com.sat.board.command.domain.article.Category

class CategoryHierarchy(
    categories: List<Category>,
) {
    private val _categoryQueries: MutableList<CategoryQuery> = mutableListOf()

    val categories: List<CategoryQuery>
        get() = _categoryQueries.toList()

    init {
        val map = categories.map { CategoryQuery.from(it) }.associateBy { it.id }
        val categoryQueries = map.values.filter { it.hasParent() }
        for (categoryQuery in categoryQueries) {
            map[categoryQuery.parentId]!!.children.add(categoryQuery)
        }
        this._categoryQueries.addAll(map.values.filter { !it.hasParent() })
    }
}
