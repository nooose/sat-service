package com.sat.user.ui.message.internal

import com.sat.board.domain.event.ArticleCreateEvent
import com.sat.board.domain.event.CommentCreateEvent
import com.sat.user.application.PointService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

private val log = KotlinLogging.logger {}

@Component
class BoardEventHandler(
    private val pointService: PointService,
) {
    @EventListener(CommentCreateEvent::class)
    fun handle(event: CommentCreateEvent) {
        try {
            pointService.commentPointAward(event.articleId, event.memberId)
        } catch (e: Exception) {
            log.error { "[댓글 작성] 포인트 적립을 실패했습니다. 댓글 ID: ${event.commentId}, 사용자 ID: ${event.memberId} " }
        }
    }

//    @EventListener(ArticleCreateEvent::class)
    @TransactionalEventListener(ArticleCreateEvent::class)
    fun handle(event: ArticleCreateEvent) {
        try {
            pointService.articlePointAward(event.memberId)
        } catch (e: Exception) {
            log.error { "[게시글 작성] 포인트 적립을 실패했습니다. 게시글 ID: ${event.articleId}, 사용자 ID: ${event.memberId} " }
        }
    }
}
