package com.sat.board.domain.port

import com.sat.board.application.query.dto.CommentWithArticle
import com.sat.board.domain.dto.CommentWithMemberDto
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor

interface CommentRepositoryCustom {
    fun findByCreatedBy(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<CommentWithArticle>>

    fun findAll(articleId: Long): List<CommentWithMemberDto>
}
