package com.sat.board.domain.port

import com.sat.board.domain.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleRepository : JpaRepository<Article, Long> {

    @Query(
        """
            select a from Article a
            join fetch a.category c
            where a.isDeleted = false
        """
    )
    fun findAllWithCategory(): List<Article>
}
