package com.sat.board.domain.port

import com.sat.board.domain.Article
import com.sat.board.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CommentRepository : JpaRepository<Comment, Long>{
    @Query("""
        select c from Comment c
        where c.article = :article and c.isDeleted = false
    """)
    fun findAll(article: Article): List<Comment>

    fun existsByParentIdIs(commentId: Long): Boolean
}
