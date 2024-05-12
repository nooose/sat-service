package com.sat.board.domain.port

import com.sat.board.domain.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long> {
    fun existsByArticleIdAndCreatedBy(articleId: Long, id: Long): Boolean
    fun deleteByArticleIdAndCreatedBy(articleId: Long, id: Long)
}
