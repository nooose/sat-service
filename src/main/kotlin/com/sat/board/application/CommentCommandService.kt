package com.sat.board.application

import com.sat.board.application.dto.command.CommentCreateCommand
import com.sat.board.application.dto.command.CommentUpdateCommand
import com.sat.board.domain.Comment
import com.sat.board.domain.Content
import com.sat.board.domain.dto.CommentDto
import com.sat.board.domain.exception.ChildExistsException
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CommentRepository
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
        checkParentId(command.parentId)
        val content = Content(command.content)
        val comment = Comment(articleId, content, command.parentId)
        commentRepository.save(comment)
    }

    private fun checkParentId(parentId: Long?) {
        if (parentId != null) {
            val exist = commentRepository.existsById(parentId)
            require(exist) { "부모 댓글이 존재하지 않습니다. - $parentId" }
        }
    }

    fun update(id: Long, command: CommentUpdateCommand) {
        val comment = getComment(id)
        val content = Content(command.content)
        val commentDto = CommentDto(content)
        comment.update(commentDto)
    }


    fun delete(id: Long) {
        val comment = getComment(id)
        val exist = commentRepository.existsByParentIdIs(id)
        if (exist) {
            throw ChildExistsException("자식이 존재하는 댓글입니다. - $id")
        }
        comment.delete()
    }

    private fun getComment(id: Long): Comment {
        return commentRepository.findByIdOrThrow(id) { "댓글이 존재하지 않습니다. - $id" }
    }
}
