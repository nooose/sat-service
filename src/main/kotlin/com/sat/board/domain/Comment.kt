package com.sat.board.domain

import com.sat.board.domain.dto.CommentDto
import com.sat.common.domain.AuditingFields
import jakarta.persistence.*

@Entity
data class Comment(
    val articleId: Long,
    @Embedded
    var content: Content,
    var parentId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditingFields() {
    private var isDeleted: Boolean = false

    fun update(that: CommentDto) {
        check(!isDeleted) { "삭제된 댓글은 수정할 수 없습니다." }
        this.content = that.content
    }

    fun delete() {
        isDeleted = true
    }
}

@Embeddable
data class Content(
    @Column(name = "content")
    val value: String
) {
}
