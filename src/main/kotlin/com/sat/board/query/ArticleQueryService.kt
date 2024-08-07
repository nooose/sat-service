package com.sat.board.query

import com.sat.board.command.domain.article.*
import com.sat.board.command.domain.comment.Comment
import com.sat.board.command.domain.like.Like
import com.sat.board.command.domain.like.LikeRepository
import com.sat.common.config.jpa.findNotNullAll
import com.sat.common.config.jpa.findOne
import com.sat.common.exception.NotFoundException
import com.sat.user.command.domain.member.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ArticleQueryService(
    private val articleRepository: ArticleRepository,
    private val likeRepository: LikeRepository,
    private val articleCacheRepository: ArticleCacheRepository,
) {
    fun get(id: Long, principalId: Long? = null): ArticleQuery {
        val article = articleRepository.findOne {
            selectNew<ArticleQuery>(
                path(Article::id),
                path(Article::title),
                path(Article::content),
                path(Article::category)(Category::name)(CategoryName::value),
                path(Article::createdBy),
                path(Member::name)
            ).from(
                entity(Article::class),
                join(Article::category),
                join(Member::class).on(path(Article::createdBy).equal(path(Member::id)))
            ).whereAnd(
                path(Article::id).equal(id),
                path(Article::isDeleted).equal(false),
            )
        } ?: throw NotFoundException("게시글을 찾을 수 없습니다. - $id")

        if (principalId == null) {
            return article
        }
        articleCacheRepository.increase(id)

        article.hasLike = likeRepository.existsByArticleIdAndCreatedBy(id, principalId)
        article.views = articleCacheRepository.getViews(id)
        return article
    }

    fun getAll(memberId: Long? = null): List<ArticleWithCountQuery> {
        val articles = articleRepository.findNotNullAll {
            selectNew<ArticleWithCountQuery>(
                path(Article::id),
                path(Article::title),
                path(Article::category)(Category::name)(CategoryName::value),
                countDistinct(entity(Comment::class)),
                countDistinct(entity(Like::class)),
                path(Article::createdDateTime)
            ).from(
                entity(Article::class),
                leftJoin(Comment::class).on(path(Article::id).equal(path(Comment::articleId))),
                leftJoin(Like::class).on(path(Article::id).equal(path(Like::articleId))),
                join(Article::category)
            ).whereAnd(
                memberId?.let { path(Article::createdBy).equal(it) },
                path(Article::isDeleted).equal(false)
            ).groupBy(
                path(Article::id)
            ).orderBy(
                path(Article::id).desc()
            )
        }

        val articlesWithViews = articleCacheRepository.getAllArticleViews()
        return articles.map {
            it.views = articlesWithViews[it.id] ?: 0
            it
        }
    }

    fun getArticlesOfViews(): List<ArticleWithViews> {
        return articleRepository.findNotNullAll {
            selectNew<ArticleWithViews>(
                path(Article::id),
                path(Article::views)
            ).from(
                entity(Article::class)
            )
        }
    }
}
