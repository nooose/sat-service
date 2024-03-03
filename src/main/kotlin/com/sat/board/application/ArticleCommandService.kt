package com.sat.board.application

import com.sat.board.application.dto.command.ArticleCreateCommand
import com.sat.board.application.dto.command.ArticleUpdateCommand
import com.sat.board.domain.Article
import com.sat.board.domain.dto.ArticleDto
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CategoryRepository
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleCommandService(
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
) {

    fun create(command: ArticleCreateCommand): Long {
        val category = categoryRepository.getReferenceById(command.categoryId)
        val article = Article(command.title, command.content, category)
        articleRepository.save(article)
        return article.id!!
    }

    fun update(id: Long, command: ArticleUpdateCommand) {
        val category = categoryRepository.getReferenceById(command.categoryId)
        val article = getArticle(id)
        val articleDto = ArticleDto(command.title, command.content, category)
        article.update(articleDto)
    }

    fun delete(id: Long) {
        val article = getArticle(id)
        article.delete()
    }

    private fun getArticle(id: Long) = articleRepository.findByIdOrThrow(id) { "게시글을 찾을 수 없습니다. - $id" }
}
