package com.sat.board.domain.port

import com.sat.board.domain.dto.ArticleWithCount

interface ArticleRepositoryCustom {

    fun getAll(): List<ArticleWithCount>
}
