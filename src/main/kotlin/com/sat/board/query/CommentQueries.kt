package com.sat.board.query

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

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

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CommentQuery(
    val memberId: Long,
    var memberName: String? = null,
    val id: Long,
    val content: String,
    val children: MutableList<CommentQuery> = mutableListOf(),
    @JsonIgnore
    val parentId: Long? = null,
    val isDeleted: Boolean,
){

    fun hasParent(): Boolean {
        return parentId != null
    }

    companion object {
        fun from(that: CommentWithMemberDto): CommentQuery {
            return CommentQuery(
                memberId = that.memberId,
                memberName = that.memberName,
                id = that.id,
                content = that.content,
                parentId = that.parentId,
                isDeleted = that.isDeleted,
            )
        }
    }
}

class CommentWithMemberDto(
    val id: Long,
    content: String,
    val memberId: Long,
    val memberName: String,
    val parentId: Long? = null,
    val isDeleted: Boolean,
) {
    val content = if (isDeleted) {
        ""
    } else {
        content
    }
}

data class CommentWithArticle(
    val id: Long,
    var content: String,
    val articleId: Long,
    val articleTitle: String,
    val createdDateTime: LocalDateTime,
)
