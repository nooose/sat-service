package com.sat.board.application.dto.query

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.sat.board.domain.dto.CommentWithMemberDto

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CommentQuery(
    val memberId: Long,
    var memberName: String? = null,
    val id: Long,
    val content: String,
    val children: MutableList<CommentQuery> = mutableListOf(),
    @JsonIgnore
    val parentId: Long? = null,
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
            )
        }
    }
}
