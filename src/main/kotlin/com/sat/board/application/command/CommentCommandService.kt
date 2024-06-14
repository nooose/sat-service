package com.sat.board.application.command

import com.sat.board.application.command.dto.CommentCreateCommand
import com.sat.board.application.command.dto.CommentUpdateCommand
import com.sat.board.domain.Comment
import com.sat.board.domain.event.CommentCreateEvent
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.board.domain.port.existParent
import com.sat.common.utils.findByIdOrThrow
import com.sat.event.utils.Events
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CommentCommandService(
    private val commentRepository: CommentRepository,
    private val articleRepository: ArticleRepository,
) {

    fun create(articleId: Long, command: CommentCreateCommand) {
        check(articleRepository.existsById(articleId)) { "게시글이 존재하지 않습니다. - $articleId" }
        checkParentId(command.parentId, articleId)

        val comment = Comment(articleId, command.content, command.parentId)
        commentRepository.save(comment)

        Events.publish(CommentCreateEvent(articleId, comment.id!!, comment.createdBy!!))
    }

    private fun checkParentId(parentId: Long?, articleId: Long) {
        if (parentId != null) {
            val exist = commentRepository.existParent(parentId, articleId)
            check(exist) { "상위 댓글이 존재하지 않습니다. - $parentId" }
        }
    }

    fun update(id: Long, command: CommentUpdateCommand, loginId: Long) {
        val comment = getComment(id)
        comment.update(command, loginId)
    }

    fun delete(id: Long, loginId: Long) {
        val comment = getComment(id)
        comment.delete(loginId)
    }

    private fun getComment(id: Long): Comment {
        return commentRepository.findByIdOrThrow(id) { "댓글이 존재하지 않습니다. - $id" }
    }
}
