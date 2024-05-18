package com.sat.board.infrastructure

import com.querydsl.core.types.ExpressionUtils.`as`
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sat.board.application.query.dto.CommentWithArticle
import com.sat.board.domain.QArticle.article
import com.sat.board.domain.QComment.comment
import com.sat.board.domain.port.CommentRepositoryCustom
import org.springframework.stereotype.Component

@Component
class CommentRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : CommentRepositoryCustom {

    override fun findByCreatedBy(memberId: Long): List<CommentWithArticle> {
        return queryFactory.select(
            Projections.constructor(
                CommentWithArticle::class.java,
                `as`(comment.id, "id"),
                `as`(comment.content, "content"),
                `as`(comment.parentId, "parentId"),
                `as`(comment.isDeleted, "isDeleted"),
                `as`(article.id, "articleId"),
                `as`(article.title, "articleTitle"),
                `as`(comment.createdDateTime, "createdDateTime"),
            ))
            .from(comment)
            .leftJoin(article).on(comment.articleId.eq(article.id))
            .where(comment.createdBy.eq(memberId))
            .fetch()
    }
}
