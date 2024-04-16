package com.sat.board.domain.dto

import com.sat.board.domain.Comment

data class CommentWithMemberDto(
    val id: Long,
    var content: String,
    val memberId: Long,
    var memberName: String,
    val parentId: Long? = null,
    val isDeleted: Boolean,
) {
    init {
        if (isDeleted) {
            content = ""
        }
    }

    companion object {
        fun from(entity: Comment, memberName: String): CommentWithMemberDto {
            return CommentWithMemberDto(
                id = entity.id!!,
                content = entity.content,
                memberId = entity.createdBy!!,
                memberName = memberName,
                parentId = entity.parentId,
                isDeleted = entity.isDeleted,
            )
        }
    }
}


