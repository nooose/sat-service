package com.sat.board.domain.port

import com.sat.board.domain.Article
import com.sat.board.domain.dto.query.ArticleWithCount

interface ArticleRepositoryCustom {

    fun getAll(principalId: Long?): List<ArticleWithCount>

    fun getLikedArticles(principalId: Long): List<Article>
}
