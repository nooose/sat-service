package com.sat.board.domain.port

import com.sat.board.domain.Article
import com.sat.board.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>{
    fun findAllByArticle(article: Article): List<Comment>

    fun existsByParentIdIs(commentId: Long): Boolean
}
