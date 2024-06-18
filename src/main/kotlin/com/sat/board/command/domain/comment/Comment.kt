package com.sat.board.command.domain.comment

import com.sat.board.command.application.CommentUpdateCommand
import com.sat.common.AuditingFields
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Comment(
    val articleId: Long,
    var content: String,
    var parentId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : AuditingFields() {
    var isDeleted: Boolean = false

    fun update(that: CommentUpdateCommand, ownerId: Long) {
        validate(ownerId)
        this.content = that.content
    }

    fun delete(ownerId: Long) {
        validate(ownerId)
        isDeleted = true
    }

    private fun validate(loginId: Long) {
        check(!isDeleted) { "삭제된 댓글은 수정하거나 삭제할 수 없습니다." }
        check(isOwner(loginId)) { "댓글 작성자만 수정하거나 삭제할 수 있습니다." }
    }
}
