package com.sat.board.domain.port

import com.sat.board.application.query.dto.CommentWithArticle

interface CommentRepositoryCustom {
    fun findByCreatedBy(memberId: Long): List<CommentWithArticle>
}
