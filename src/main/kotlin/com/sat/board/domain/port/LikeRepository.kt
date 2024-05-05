package com.sat.board.domain.port

import com.sat.board.domain.Like
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<Like, Long> {
    fun existsByArticleIdAndMemberId(articleId: Long, id: Long): Boolean
    fun deleteByArticleIdAndMemberId(articleId: Long, id: Long)
}
