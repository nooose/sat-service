package com.sat.board.domain

import com.sat.board.domain.dto.CommentDto
import jakarta.persistence.*

@Entity
data class Comment(
    @ManyToOne @JoinColumn(name = "article_id")
    val article: Article,
    @Embedded
    var content: Content,
    var parentId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    fun update(that: CommentDto) {
        this.content = that.content
    }
}

@Embeddable
data class Content(
    @Column(name = "content")
    val value: String
) {
}
