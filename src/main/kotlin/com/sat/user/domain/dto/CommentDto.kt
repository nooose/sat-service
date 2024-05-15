package com.sat.user.domain.dto

import com.sat.board.application.query.dto.CommentWithArticle
import java.time.LocalDate

data class CommentDto(
    val id: Long,
    val content: String,
    val parentId: Long? = null,
    val isDeleted: Boolean,
    val articleId: Long,
    val articleTitle: String,
    val createdAt: LocalDate,
) {
    companion object {
        fun from(commentWithArticle: CommentWithArticle): CommentDto {
            return CommentDto(
                id = commentWithArticle.id,
                content = commentWithArticle.content,
                parentId = commentWithArticle.parentId,
                isDeleted = commentWithArticle.isDeleted,
                articleId = commentWithArticle.articleId,
                articleTitle = commentWithArticle.articleTitle,
                createdAt = commentWithArticle.createdAt.toLocalDate(),
            )
        }
    }
}
