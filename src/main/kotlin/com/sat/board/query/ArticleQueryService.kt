package com.sat.board.query

import com.sat.board.command.domain.article.Article
import com.sat.board.command.domain.article.ArticleRepository
import com.sat.board.command.domain.article.Category
import com.sat.board.command.domain.article.CategoryName
import com.sat.board.command.domain.comment.Comment
import com.sat.board.command.domain.like.Like
import com.sat.board.command.domain.like.LikeRepository
import com.sat.common.CursorRequest
import com.sat.common.PageCursor
import com.sat.common.config.jpa.findOne
import com.sat.common.config.jpa.limit
import com.sat.common.exception.NotFoundException
import com.sat.user.command.domain.member.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ArticleQueryService(
    private val articleRepository: ArticleRepository,
    private val likeRepository: LikeRepository,
) {
    fun get(id: Long, principalId: Long? = null): ArticleQuery {
        val article = articleRepository.findOne {
            selectNew<ArticleQuery>(
                path(Article::id),
                path(Article::title),
                path(Article::category)(Category::name),
                path(Article::createdBy),
                path(Member::name)
            ).from(
                entity(Article::class),
                join(Article::category),
                join(entity(Member::class)).on(path(Article::createdBy).equal(path(Member::id)))
            ).where(
                path(Article::isDeleted).equal(false)
            )
        } ?: throw NotFoundException("게시글을 찾을 수 없습니다. - $id")

        if (principalId == null) {
            return article
        }
        article.hasLike = likeRepository.existsByArticleIdAndCreatedBy(id, principalId)
        return article
    }

    fun getAll(memberId: Long? = null): List<ArticleWithCountQuery> {
        return articleRepository.findAll {
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
        }.filterNotNull()
    }

    // TODO: 인덱스 추가
    fun getLikedArticles(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<LikedArticleSimpleQuery>> {
        val likedArticles = articleRepository.findAll {
            selectNew<LikedArticleSimpleQuery>(
                path(Like::id),
                path(Like::articleId),
                path(Article::title),
                path(Article::createdDateTime),
            ).from(
                entity(Like::class),
                join(Article::class).on(path(Like::articleId).equal(path(Article::id)))
            ).whereAnd(
                cursorRequest.id?.let { path(Like::id).lessThan(it) },
                path(Like::createdBy).equal(memberId)
            ).orderBy(
                path(Like::id).desc()
            ).limit(cursorRequest.size)
        }.filterNotNull()

        return cursorRequest.nextFrom(likedArticles)
    }
}
