package com.sat.board.domain.port

import com.sat.board.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


fun CommentRepository.existParent(commentId: Long, articleId: Long): Boolean {
    return existsByIdIsAndArticleIdIs(commentId, articleId)
}

interface CommentRepository : JpaRepository<Comment, Long>{
    @Query("""
        select c from Comment c
        where c.articleId = :articleId
    """)
    fun findAll(articleId: Long): List<Comment>

    fun existsByIdIsAndArticleIdIs(parentId: Long, articleId: Long): Boolean
}
