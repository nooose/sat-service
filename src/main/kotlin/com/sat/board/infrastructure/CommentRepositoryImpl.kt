package com.sat.board.infrastructure

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sat.board.application.query.dto.CommentWithArticle
import com.sat.board.domain.QArticle.article
import com.sat.board.domain.QComment.comment
import com.sat.board.domain.dto.CommentWithMemberDto
import com.sat.board.domain.port.CommentRepositoryCustom
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.user.domain.QMember.member
import org.springframework.stereotype.Component

@Component
class CommentRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : CommentRepositoryCustom {

    override fun findByCreatedBy(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<CommentWithArticle>> {
        val comments = queryFactory.select(
            Projections.constructor(
                CommentWithArticle::class.java,
                comment.id,
                comment.content,
                article.id,
                article.title,
                comment.createdDateTime,
            )
        )
            .from(comment)
            .join(article).on(comment.articleId.eq(article.id))
            .where(
                lessThanId(cursorRequest.id),
                comment.createdBy.eq(memberId),
                comment.isDeleted.eq(false),
            )
            .orderBy(comment.id.desc())
            .limit(cursorRequest.size)
            .fetch()
        return PageCursor(cursorRequest.next(getNextId(comments)), comments)
    }

    override fun findAll(articleId: Long): List<CommentWithMemberDto> {
        val comments = queryFactory.select(
            Projections.constructor(
                CommentWithMemberDto::class.java,
                comment.id,
                comment.content,
                comment.createdBy,
                member.name,
                comment.parentId,
                comment.isDeleted,
            )
        )
            .from(comment)
            .join(member).on(comment.createdBy.eq(member.id))
            .where(
                comment.articleId.eq(articleId),
            )
            .fetch()
        return comments
    }

    private fun lessThanId(id: Long?): BooleanExpression? {
        return id?.let { comment.id.lt(it) }
    }

    private fun getNextId(comments: List<CommentWithArticle>): Long {
        if (comments.isEmpty()) {
            return 0
        }
        return comments.minOf { it.id }
    }
}
