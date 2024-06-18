package com.sat.board.command.application

import com.sat.board.command.domain.article.*
import com.sat.board.command.domain.like.Like
import com.sat.board.command.domain.like.LikeRepository
import com.sat.common.utils.findByIdOrThrow
import com.sat.event.utils.Events
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ArticleCommandService(
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
    private val likeRepository: LikeRepository,
) {

    fun create(command: ArticleCreateCommand): Long {
        val category = categoryRepository.getReferenceById(command.categoryId)
        val article = Article(command.title, command.content, category)
        articleRepository.save(article)

        Events.publish(ArticleCreateEvent(article.createdBy!!, article.id))
        return article.id
    }

    fun update(id: Long, command: ArticleUpdateCommand) {
        val article = getArticle(id)
        val articleDto = ArticleUpdateDto(command.title, command.content)
        article.update(articleDto)
    }

    fun delete(id: Long) {
        val article = getArticle(id)
        article.delete()
    }

    private fun getArticle(id: Long) = articleRepository.findByIdOrThrow(id) { "게시글을 찾을 수 없습니다. - $id" }

    fun like(articleId: Long, principalId: Long) {
        val like = likeRepository.findByArticleIdAndCreatedBy(articleId, principalId)
        if (like != null) {
            likeRepository.delete(like)
            return
        }

        likeRepository.save(Like(articleId))
    }
}
