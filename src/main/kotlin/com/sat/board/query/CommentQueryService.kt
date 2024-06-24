package com.sat.board.query

import com.sat.board.command.domain.article.ArticleRepository
import com.sat.board.command.domain.comment.Comment
import com.sat.board.command.domain.comment.CommentRepository
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
    fun getComments(articleId: Long): List<CommentQuery> {
        val article = articleRepository.findByIdOrNull(articleId)
            ?: throw IllegalStateException("게시글이 존재하지 않습니다. - $articleId")
        check(!article.isDeleted) { "삭제된 게시글 입니다" }

        val comments = commentRepository.findAll {
            selectNew<CommentWithMemberQuery>(
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
}
