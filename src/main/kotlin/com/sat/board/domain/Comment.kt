package com.sat.board.domain

import com.sat.board.application.dto.command.CommentUpdateCommand
import com.sat.common.domain.AuditingFields
import jakarta.persistence.*

@Table(name = "comments")
@Entity
data class Comment(
    val articleId: Long,
    var content: String,
    var parentId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditingFields() {
    private var isDeleted: Boolean = false

    fun update(that: CommentUpdateCommand, loginId: Long) {
        check(!isDeleted) { "삭제된 댓글은 수정할 수 없습니다." }
        check(this.createdBy == loginId) { "댓글 작성자만 수정할 수 있습니다." }
        this.content = that.content
    }

    fun delete(loginId: Long) {
        check(!this.isDeleted) { "삭제된 댓글 입니다." }
        check(this.createdBy == loginId) { "댓글 작성자만 삭제할 수 있습니다." }
        isDeleted = true
    }
}
