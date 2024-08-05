package com.sat.board.command.domain.comment

import org.springframework.data.jpa.repository.JpaRepository


fun CommentRepository.existParent(commentId: Long, articleId: Long): Boolean {
    return existsByIdIsAndArticleIdIs(commentId, articleId)
}

interface CommentRepository : JpaRepository<Comment, Long> {
    fun existsByIdIsAndArticleIdIs(parentId: Long, articleId: Long): Boolean
}
