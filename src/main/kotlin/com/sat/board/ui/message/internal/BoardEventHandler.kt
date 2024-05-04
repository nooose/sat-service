package com.sat.board.ui.message.internal

import com.sat.board.domain.event.ArticleCreateEvent
import com.sat.board.domain.event.CommentCreateEvent
import com.sat.user.application.PointService
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class BoardEventHandler(
    private val pointService: PointService,
) {
    @TransactionalEventListener(CommentCreateEvent::class)
    fun handle(event: CommentCreateEvent) {
        pointService.commentPointAward(event.articleId, event.memberId, event.pointType)
    }

    @TransactionalEventListener(ArticleCreateEvent::class)
    fun handle(event: ArticleCreateEvent) {
        pointService.articlePointAward(event.memberId, event.pointType)
    }
}
