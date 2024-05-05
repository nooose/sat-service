package com.sat.board.domain.port

import com.sat.board.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long>, ArticleRepositoryCustom
