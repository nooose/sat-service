package com.sat.board.application.query

import com.sat.board.application.query.dto.CommentHierarchy
import com.sat.board.application.query.dto.CommentQuery
import com.sat.board.application.query.dto.CommentWithArticle
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CommentQueryService(
    private val articleRepository: ArticleRepository,
    private val commentRepository: CommentRepository,
) {
    fun get(articleId: Long): List<CommentQuery> {
        val article =
            articleRepository.findByIdOrNull(articleId)
                ?: throw IllegalStateException("게시글이 존재하지 않습니다. - $articleId")

        if (article.isDeleted) {
            throw IllegalStateException("삭제된 게시글 입니다")
        }

        val commentWithMember = commentRepository.findAll(articleId)
        val hierarchy = CommentHierarchy(commentWithMember)
        return hierarchy.comments
    }

    fun getComments(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<CommentWithArticle>> {
        return commentRepository.findByCreatedBy(memberId, cursorRequest)
    }
}
