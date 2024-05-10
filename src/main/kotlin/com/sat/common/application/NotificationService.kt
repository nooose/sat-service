package com.sat.common.application

import com.sat.board.domain.event.CommentCreateEvent
import com.sat.board.domain.port.ArticleRepository
import com.sat.common.domain.dto.query.NotificationQuery
import com.sat.common.domain.exception.NotificationException
import com.sat.common.infrastructure.EmitterRepository
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.time.Duration

private val DEFAULT_TIMEOUT = Duration.ofHours(1).toMillis()

@Transactional(readOnly = true)
@Service
class NotificationService(
    private val emitterRepository: EmitterRepository,
    private val articleRepository: ArticleRepository,
) {

    fun connect(memberId: Long): SseEmitter {
        val sseEmitter = SseEmitter(DEFAULT_TIMEOUT)
        emitterRepository.save(memberId, sseEmitter)

        try {
            val data = SseEmitter.event()
                .name("notification")
                .data(NotificationQuery.connect())
            sseEmitter.send(data)
        } catch (exception: IOException) {
            println(exception)
            throw IllegalStateException("이벤트 구독 실패: $exception")
        }
        sseEmitter.onCompletion {
            println("completion")
            emitterRepository.delete(memberId)
        }
        sseEmitter.onTimeout {
            println("timeout")
            sseEmitter.complete()
        }
        return sseEmitter
    }

    fun comment(event: CommentCreateEvent) {
        val article = articleRepository.findByIdOrThrow(event.articleId) { throw NotificationException("게시글이 없습니다. - ${event.articleId}") }
        val articleOwner = article.createdBy!!

        val isNotArticleOwner = event.commentOwnerId != articleOwner
        if (isNotArticleOwner) {
            val sseEmitter = emitterRepository.findBy(articleOwner)
            val data = SseEmitter.event()
                .name("notification")
                .data(NotificationQuery("${article.title} 게시글에", "댓글이 달렸습니다"))
            sseEmitter?.send(data)
        }
    }
}
