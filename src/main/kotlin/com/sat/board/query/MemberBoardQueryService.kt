package com.sat.board.query

import com.sat.board.command.domain.article.Article
import com.sat.board.command.domain.comment.Comment
import com.sat.board.command.domain.like.Like
import com.sat.common.CursorRequest
import com.sat.common.JdslRepository
import com.sat.common.PageCursor
import com.sat.common.config.jpa.limit
import com.sat.user.query.BoardQueryService
import com.sat.user.query.CommentWithArticleQuery
import com.sat.user.query.LikedArticleSimpleQuery
import org.springframework.stereotype.Service

// TODO: 인덱스 추가
@Service
class MemberBoardQueryService(
    private val articleQueryService: ArticleQueryService,
    private val jdslRepository: JdslRepository,
) : BoardQueryService {
    override fun getArticles(memberId: Long): List<ArticleWithCountQuery> {
        return articleQueryService.getAll(memberId)
    }

    override fun getComments(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<CommentWithArticleQuery>> {
        val comments = jdslRepository.findAll {
            selectNew<CommentWithArticleQuery>(
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
        }
        return cursorRequest.nextFrom(comments)
    }

    override fun getLikedArticles(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<LikedArticleSimpleQuery>> {
        val likedArticles = jdslRepository.findAll {
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
        }
        return cursorRequest.nextFrom(likedArticles)
    }
}
