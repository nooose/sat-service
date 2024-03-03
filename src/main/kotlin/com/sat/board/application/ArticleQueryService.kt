package com.sat.board.application

import com.sat.board.application.dto.query.ArticleQuery
import com.sat.board.domain.port.ArticleRepository
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ArticleQueryService(
    private val articleRepository: ArticleRepository,
) {

    fun get(id: Long): ArticleQuery {
        val article = articleRepository.findByIdOrThrow(id) { "게시글을 찾을 수 없습니다. - $id" }
        return ArticleQuery.from(article)
    }

    fun get(): List<ArticleQuery> {
        return articleRepository.findAllWithCategory()
            .map { ArticleQuery.from(it) }
    }
}
