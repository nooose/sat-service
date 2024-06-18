package com.sat.board.query

import com.sat.common.Cursor
import java.time.LocalDateTime

data class LikedArticleSimpleQuery(
    override val id: Long,
    val articleId: Long,
    val articleTitle: String,
    val createdDateTime: LocalDateTime,
) : Cursor
