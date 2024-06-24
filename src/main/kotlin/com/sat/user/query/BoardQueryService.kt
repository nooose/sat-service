package com.sat.user.query

import com.sat.board.query.ArticleWithCountQuery
import com.sat.common.CursorRequest
import com.sat.common.PageCursor

interface BoardQueryService {
    fun getArticles(memberId: Long): List<ArticleWithCountQuery>
    fun getComments(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<CommentWithArticleQuery>>
    fun getLikedArticles(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<LikedArticleSimpleQuery>>
}
