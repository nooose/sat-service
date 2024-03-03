package com.sat.board.application

import com.sat.board.application.dto.command.ArticleCreateCommand
import com.sat.board.domain.Article
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CategoryRepository
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
}
