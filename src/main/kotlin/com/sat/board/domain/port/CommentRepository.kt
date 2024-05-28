package com.sat.board.domain.port

import com.sat.board.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository


fun CommentRepository.existParent(commentId: Long, articleId: Long): Boolean {
    return existsByIdIsAndArticleIdIs(commentId, articleId)
}

interface CommentRepository : JpaRepository<Comment, Long>, CommentRepositoryCustom {
    fun existsByIdIsAndArticleIdIs(parentId: Long, articleId: Long): Boolean
}
