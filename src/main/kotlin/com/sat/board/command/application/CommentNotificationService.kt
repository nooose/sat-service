package com.sat.board.command.application

import com.sat.board.command.domain.article.ArticleRepository
import com.sat.board.command.domain.comment.CommentNotification
import com.sat.event.domain.NotificationEvent
import com.sat.event.utils.Events
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CommentNotificationService(
    private val articleRepository: ArticleRepository,
) {

    fun notify(articleId: Long, commentOwnerId: Long) {
        val article = articleRepository.findByIdOrNull(articleId) ?: return
        if (article.isOwner(commentOwnerId)) {
            return
        }

        val notification = CommentNotification(
            title = "${article.title} 게시글에 댓글이 달렸습니다",
            articleId = articleId
        )
        Events.publish(NotificationEvent(article.createdBy!!, notification))
    }
}
