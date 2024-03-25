package com.sat.board.domain.dto

import com.sat.board.domain.Comment

data class CommentWithMemberDto(
    val id: Long,
    val content: String,
    val memberId: Long,
    var memberName: String? = null,
    val parentId: Long? = null,
) {
    companion object {
        fun from(entity: Comment): CommentWithMemberDto {
            return CommentWithMemberDto(
                id = entity.id!!,
                content = entity.content,
                memberId = entity.createdBy!!,
                parentId = entity.parentId,
            )
        }
    }
}


