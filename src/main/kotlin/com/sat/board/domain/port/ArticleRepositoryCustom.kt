package com.sat.board.domain.port

import com.sat.board.domain.dto.query.ArticleWithCount
import com.sat.board.domain.dto.query.LikedArticleSimpleQuery
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor

interface ArticleRepositoryCustom {

    fun getAll(principalId: Long?): List<ArticleWithCount>

    fun getLikedArticles(principalId: Long, cursorRequest: CursorRequest): PageCursor<List<LikedArticleSimpleQuery>>
}
