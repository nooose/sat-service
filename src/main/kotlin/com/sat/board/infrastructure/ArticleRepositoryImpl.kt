package com.sat.board.infrastructure

import com.querydsl.core.types.ExpressionUtils.`as`
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sat.board.domain.QArticle.article
import com.sat.board.domain.QCategory.category
import com.sat.board.domain.QComment.comment
import com.sat.board.domain.QLike.like
import com.sat.board.domain.dto.query.ArticleWithCount
import com.sat.board.domain.port.ArticleRepositoryCustom
import org.springframework.stereotype.Component

@Component
class ArticleRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : ArticleRepositoryCustom {

    override fun getAll(principalId: Long?): List<ArticleWithCount> {
        return queryFactory.select(
            Projections.constructor(
                ArticleWithCount::class.java,
                `as`(article.id, "id"),
                `as`(article.title, "title"),
                `as`(category.name.value, "category"),
                `as`(comment.countDistinct(), "commentCount"),
                `as`(like.countDistinct(), "likeCount"),
            ))
            .from(article)
            .leftJoin(comment).on(article.id.eq(comment.articleId))
            .leftJoin(like).on(article.id.eq(like.articleId))
            .join(category).on(article.category.id.eq(category.id))
            .where(
                eqOwner(principalId)
            )
            .groupBy(article.id)
            .fetch()
    }

    private fun eqOwner(id: Long?): BooleanExpression? {
        return id?.let { article.createdBy.eq(it) }
    }
}
