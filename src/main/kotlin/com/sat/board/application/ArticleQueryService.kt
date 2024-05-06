package com.sat.board.application

import com.sat.board.application.dto.query.ArticleQuery
import com.sat.board.domain.dto.ArticleWithCount
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.LikeRepository
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
    fun get(id: Long, principalId: Long): ArticleQuery {
        val article = articleRepository.findByIdOrThrow(id) { "게시글을 찾을 수 없습니다. - $id" }
        val hasLike = likeRepository.existsByArticleIdAndMemberId(id, principalId)
        return ArticleQuery.from(article, hasLike)
    }

    fun get(): List<ArticleWithCount> {
        return articleRepository.getAll()
    }
}
