package com.sat.board.application

import com.sat.board.application.dto.query.CommentHierarchy
import com.sat.board.application.dto.query.CommentQuery
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CommentQueryService(
    private val commentRepository: CommentRepository,
    private val articleRepository: ArticleRepository,
) {
    fun get(articleId: Long): List<CommentQuery> {
        val article = articleRepository.findByIdOrThrow(articleId) { "게시글이 존재하지 않습니다." }
        val comments = commentRepository.findAllByArticle(article)
        val hierarchy = CommentHierarchy(comments)
        return hierarchy.comments
    }
}
