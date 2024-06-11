package com.sat.board.domain.port

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.sat.board.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository


fun CommentRepository.existParent(commentId: Long, articleId: Long): Boolean {
    return existsByIdIsAndArticleIdIs(commentId, articleId)
}

interface CommentRepository : JpaRepository<Comment, Long>, KotlinJdslJpqlExecutor {
    fun existsByIdIsAndArticleIdIs(parentId: Long, articleId: Long): Boolean
}
