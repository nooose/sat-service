package com.sat.user.query

import com.sat.common.CursorItem
import java.time.LocalDateTime

data class LikedArticleSimpleQuery(
    override val id: Long,
    val articleId: Long,
    val articleTitle: String,
    val createdDateTime: LocalDateTime,
) : CursorItem

data class CommentWithArticleQuery(
    override val id: Long,
    var content: String,
    val articleId: Long,
    val articleTitle: String,
    val createdDateTime: LocalDateTime,
) : CursorItem
