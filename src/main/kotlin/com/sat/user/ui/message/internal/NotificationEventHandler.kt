package com.sat.user.ui.message.internal

import com.sat.board.domain.event.CommentCreateEvent
import com.sat.user.application.NotificationService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationEventHandler(
    private val notificationService: NotificationService,
) {

    @EventListener(CommentCreateEvent::class)
    fun handle(event: CommentCreateEvent) {
        notificationService.notify(event.articleId, event.commentOwnerId)
    }
}
