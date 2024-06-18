package com.sat.board.ui.message

import com.sat.board.command.application.CommentNotificationService
import com.sat.board.command.domain.comment.CommentCreateEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CommentEventHandler(
    private val commentNotificationService: CommentNotificationService,
) {
    @EventListener(CommentCreateEvent::class)
    fun handle(event: CommentCreateEvent) {
        commentNotificationService.notify(event.articleId, event.commentOwnerId)
    }
}
