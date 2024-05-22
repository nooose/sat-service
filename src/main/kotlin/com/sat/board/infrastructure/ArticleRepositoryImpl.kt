package com.sat.board.infrastructure

import com.querydsl.core.types.ExpressionUtils.`as`
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sat.board.domain.dto.query.LikedArticleSimpleQuery
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
                `as`(comment.countDistinct().intValue(), "commentCount"),
                `as`(like.countDistinct().intValue(), "likeCount"),
                `as`(article.createdDateTime, "createdDateTime"),
            ))
            .from(article)
            .leftJoin(comment).on(article.id.eq(comment.articleId))
            .leftJoin(like).on(article.id.eq(like.articleId))
            .join(category).on(article.category.id.eq(category.id))
            .where(
                eqOwner(principalId),
                article.isDeleted.eq(false)
            )
            .groupBy(article.id)
            .orderBy(article.id.desc())
            .fetch()
    }

    private fun eqOwner(id: Long?): BooleanExpression? {
        return id?.let { article.createdBy.eq(it) }
    }

    override fun getLikedArticles(principalId: Long): List<LikedArticleSimpleQuery> {
        // TODO: 인덱스 추가
        return queryFactory.select(
            Projections.constructor(
                LikedArticleSimpleQuery::class.java,
                `as`(like.articleId,"articleId"),
                `as`(article.title,"title"),
                `as`(article.createdDateTime,"createDateTime")
            ))
            .from(like)
            .join(article).on(like.articleId.eq(article.id))
            .where(like.createdBy.eq(principalId))
            .orderBy(like.id.desc())
            .fetch()
    }

}
