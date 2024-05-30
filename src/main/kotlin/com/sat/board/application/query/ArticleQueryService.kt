package com.sat.board.application.query

import com.sat.board.application.query.dto.ArticleQuery
import com.sat.board.domain.dto.query.ArticleWithCount
import com.sat.board.domain.dto.query.LikedArticleSimpleQuery
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.LikeRepository
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ArticleQueryService(
    private val articleRepository: ArticleRepository,
    private val likeRepository: LikeRepository,
) {

    // TODO: 지금은 테이블 3개를 각각 조회하고 있음, Querydsl 로직으로 변경
    fun get(id: Long, principalId: Long? = null): ArticleQuery {
        val article = articleRepository.findByIdOrThrow(id) { "게시글을 찾을 수 없습니다. - $id" }
        if (article.isDeleted) {
            throw IllegalStateException("삭제된 게시글 입니다.")
        }

        if (principalId == null) {
            return ArticleQuery.from(article, false)
        }
        val hasLike = likeRepository.existsByArticleIdAndCreatedBy(id, principalId)
        return ArticleQuery.from(article, hasLike)
    }

    fun getAll(memberId: Long? = null): List<ArticleWithCount> {
        return articleRepository.getAll(memberId)
    }

    fun getLikedArticles(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<LikedArticleSimpleQuery>> {
        return articleRepository.getLikedArticles(memberId, cursorRequest)
    }
}
