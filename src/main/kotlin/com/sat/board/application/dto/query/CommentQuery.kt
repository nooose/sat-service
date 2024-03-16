package com.sat.board.application.dto.query

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.sat.board.domain.Comment

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CommentQuery(
    val articleId: Long,
    val id: Long,
    val content: String,
    val children: MutableList<CommentQuery> = mutableListOf(),
    @JsonIgnore
    val parentId: Long? = null,
){
    companion object {
        fun from(entity: Comment): CommentQuery {
            return CommentQuery(
                articleId = entity.article.id!!,
                id = entity.id!!,
                content =  entity.content,
                parentId = entity.parentId,
            )
        }
    }
}
