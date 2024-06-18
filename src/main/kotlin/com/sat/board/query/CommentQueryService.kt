package com.sat.board.query

import com.sat.board.command.domain.article.Article
import com.sat.board.command.domain.article.ArticleRepository
import com.sat.board.command.domain.comment.Comment
import com.sat.board.command.domain.comment.CommentRepository
import com.sat.common.config.jpa.limit
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.user.command.domain.member.Member
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
        val article = articleRepository.findByIdOrNull(articleId)
            ?: throw IllegalStateException("게시글이 존재하지 않습니다. - $articleId")
        check(!article.isDeleted) { "삭제된 게시글 입니다" }

        val comments = commentRepository.findAll {
            selectNew<CommentWithMemberDto>(
                path(Comment::id),
                path(Comment::content),
                path(Comment::createdBy),
                path(Member::name),
                path(Comment::parentId),
                path(Comment::isDeleted),
            ).from(
                entity(Comment::class),
                join(Member::class).on(path(Comment::createdBy).equal(path(Member::id)))
            ).where(
                path(Comment::articleId).equal(articleId)
            )
        }.filterNotNull()

        val hierarchy = CommentHierarchy(comments)
        return hierarchy.comments
    }

    fun getComments(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<CommentWithArticle>> {
        val comments = commentRepository.findAll {
            selectNew<CommentWithArticle>(
                path(Comment::id),
                path(Comment::content),
                path(Article::id),
                path(Article::title),
                path(Comment::createdDateTime),
            ).from(
                entity(Comment::class),
                join(Article::class).on(path(Comment::articleId).equal(path(Article::id))),
            ).whereAnd(
                cursorRequest.id?.let { path(Comment::id).lessThan(it) },
                path(Comment::createdBy).equal(memberId),
                path(Comment::isDeleted).equal(false),
            ).orderBy(
                path(Comment::id).desc()
            ).limit(cursorRequest.size)
        }.filterNotNull()
        return PageCursor(cursorRequest.next(getNextId(comments)), comments)
    }

    private fun getNextId(likes: List<CommentWithArticle>): Long {
        if (likes.isEmpty()) {
            return 0
        }
        return likes.minOf { it.id }
    }
}
