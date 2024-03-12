package com.sat.board.application

import com.sat.board.application.dto.command.CommentCreateCommand
import com.sat.board.domain.Comment
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
        val article = articleRepository.findByIdOrThrow(articleId) { "게시글이 존재하지 않습니다. - $articleId" }
        if (command.parentId != null) {
            commentRepository.findByIdOrThrow(command.parentId) { "부모 댓글이 존재하지 않습니다. - ${command.parentId}" }
        }
        val comment = Comment(article, command.content, command.parentId)
        commentRepository.save(comment)
    }
}
