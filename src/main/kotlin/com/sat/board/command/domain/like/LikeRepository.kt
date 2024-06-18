package com.sat.board.command.domain.like

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long>, KotlinJdslJpqlExecutor {
    fun existsByArticleIdAndCreatedBy(articleId: Long, id: Long): Boolean
    fun deleteByArticleIdAndCreatedBy(articleId: Long, id: Long)
}
