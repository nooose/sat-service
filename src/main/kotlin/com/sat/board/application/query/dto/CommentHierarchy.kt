package com.sat.board.application.query.dto

import com.sat.board.domain.dto.CommentWithMemberDto

class CommentHierarchy(
    comments: List<CommentWithMemberDto>,
) {
    private val _commentQueries: MutableList<CommentQuery> = mutableListOf()

    val comments: List<CommentQuery>
        get() = _commentQueries.toList()

    init {
        val map = comments.map { CommentQuery.from(it) }.associateBy { it.id }
        val commentQueries = map.values.filter { it.hasParent() }
        for (commentQuery in commentQueries) {
            map[commentQuery.parentId]!!.children.add(commentQuery)
        }
        this._commentQueries.addAll(map.values.filter { !it.hasParent() })
    }
}
