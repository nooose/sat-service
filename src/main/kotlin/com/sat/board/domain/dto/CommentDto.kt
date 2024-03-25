package com.sat.board.domain.dto

import com.sat.board.domain.Comment

data class CommentDto(
    val memberId: Long,
    var memberName: String? = null,
    val id: Long,
    val content: String,
    val parentId: Long? = null,
) {
    companion object {
        fun from(entity: Comment): CommentDto {
            return CommentDto(
                memberId = entity.createdBy!!,
                id = entity.id!!,
                parentId = entity.parentId,
                content = entity.content,
            )
        }
    }
}


