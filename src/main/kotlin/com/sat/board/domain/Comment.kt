package com.sat.board.domain

import com.sat.board.application.dto.command.CommentUpdateCommand
import jakarta.persistence.*

@Entity
data class Comment(
    @ManyToOne @JoinColumn(name = "article_id")
    val article: Article,
    var content: String,
    var parentId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    fun update(command: CommentUpdateCommand) {
        this.content = command.content
    }
}
