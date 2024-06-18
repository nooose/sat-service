package com.sat.board.query

import com.sat.board.command.domain.article.Article
import com.sat.board.command.domain.article.ArticleRepository
import com.sat.board.command.domain.article.Category
import com.sat.board.command.domain.article.CategoryName
import com.sat.board.command.domain.comment.Comment
import com.sat.board.command.domain.like.Like
import com.sat.board.command.domain.like.LikeRepository
import com.sat.common.config.jpa.limit
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.common.utils.findByIdOrThrow
import com.sat.user.command.domain.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ArticleQueryService(
    private val articleRepository: ArticleRepository,
    private val likeRepository: LikeRepository,
    private val memberRepository: MemberRepository,
) {

    fun get(id: Long, principalId: Long? = null): ArticleQuery {
        val article = articleRepository.findByIdOrThrow(id) { "게시글을 찾을 수 없습니다. - $id" }
        if (article.isDeleted) {
            throw IllegalStateException("삭제된 게시글 입니다.")
        }

        val member = memberRepository.findByIdOrThrow(article.createdBy!!)
            { "존재하지 않는 유저입니다. - ${article.createdBy}" }

        if (principalId == null) {
            return ArticleQuery.from(article, member.name, false)
        }
        val hasLike = likeRepository.existsByArticleIdAndCreatedBy(id, principalId)
        return ArticleQuery.from(article, member.name, hasLike)
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

        return PageCursor(cursorRequest.next(getNextId(likedArticles)), likedArticles)
    }

    private fun getNextId(likes: List<LikedArticleSimpleQuery>): Long {
        if (likes.isEmpty()) {
            return 0
        }
        return likes.minOf { it.id }
    }
}
