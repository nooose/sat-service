package com.sat.board.domain.dto

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


