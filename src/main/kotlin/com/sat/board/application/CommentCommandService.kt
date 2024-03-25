package com.sat.board.application

import com.sat.board.application.dto.command.CommentCreateCommand
import com.sat.board.application.dto.command.CommentUpdateCommand
import com.sat.board.domain.Comment
import com.sat.board.domain.exception.ChildExistsException
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.board.domain.port.hasParent
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CommentCommandService(
    private val commentRepository: CommentRepository,
    private val articleRepository: ArticleRepository,
) {

    fun create(articleId: Long, command: CommentCreateCommand) {
        articleRepository.findByIdOrThrow(articleId) { "게시글이 존재하지 않습니다. - $articleId" }
        checkParentId(command.parentId, articleId)
        val comment = Comment(articleId, command.content, command.parentId)
        commentRepository.save(comment)
    }

    private fun checkParentId(parentId: Long?, articleId: Long) {
        if (parentId != null) {
            val exist = commentRepository.hasParent(parentId, articleId)
            require(exist) { "부모 댓글이 존재하지 않습니다. - $parentId" }
        }
    }

    fun update(id: Long, command: CommentUpdateCommand, loginId: Long) {
        val comment = getComment(id)
        comment.update(command, loginId)
    }


    fun delete(id: Long, loginId: Long) {
        val comment = getComment(id)
        val exist = commentRepository.hasParent(id)
        if (exist) {
            throw ChildExistsException("자식이 존재하는 댓글입니다. - $id")
        }
        comment.delete(loginId)
    }

    private fun getComment(id: Long): Comment {
        return commentRepository.findByIdOrThrow(id) { "댓글이 존재하지 않습니다. - $id" }
    }
}
