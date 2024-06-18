package com.sat.board.query

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.sat.common.Cursor
import java.time.LocalDateTime


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
        fun from(that: CommentWithMemberQuery): CommentQuery {
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

class CommentWithMemberQuery(
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

data class CommentWithArticleQuery(
    override val id: Long,
    var content: String,
    val articleId: Long,
    val articleTitle: String,
    val createdDateTime: LocalDateTime,
) : Cursor
