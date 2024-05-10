package com.sat.user.application

import com.sat.board.domain.port.ArticleRepository
import com.sat.common.application.NotificationDto
import com.sat.common.application.NotificationProcessor
import com.sat.common.domain.exception.NotificationException
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional(readOnly = true)
@Service
class NotificationService(
    private val articleRepository: ArticleRepository,
    private val processor: NotificationProcessor,
) {

    fun notify(articleId: Long, commentOwnerId: Long) {
        val article = articleRepository.findByIdOrThrow(articleId) {
            throw NotificationException("게시글이 없습니다. - $articleId")
        }

        if (article.isOwner(commentOwnerId)) {
            return
        }

        processor.send(
            article.createdBy!!,
            "comment-notification",
            NotificationDto("${article.title} 게시글에 댓글이 달렸습니다")
        )
    }
}
