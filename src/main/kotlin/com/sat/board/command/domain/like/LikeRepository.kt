package com.sat.board.command.domain.like

import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long> {
    fun findByArticleIdAndCreatedBy(articleId: Long, id: Long): Like?
    fun existsByArticleIdAndCreatedBy(articleId: Long, id: Long): Boolean
}
