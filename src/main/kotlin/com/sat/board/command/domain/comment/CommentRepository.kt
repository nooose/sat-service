package com.sat.board.command.domain.comment

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository


fun CommentRepository.existParent(commentId: Long, articleId: Long): Boolean {
    return existsByIdIsAndArticleIdIs(commentId, articleId)
}

interface CommentRepository : JpaRepository<Comment, Long>, KotlinJdslJpqlExecutor {
    fun existsByIdIsAndArticleIdIs(parentId: Long, articleId: Long): Boolean
}
