package com.sat.user.ui.message.internal

import com.sat.board.domain.event.CommentCreateEvent
import com.sat.common.application.NotificationService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NotificationEventHandler(
    private val notificationService: NotificationService,
) {

    @Transactional
    @EventListener(CommentCreateEvent::class)
    fun handle(event: CommentCreateEvent) {
        notificationService.comment(event)
    }
}
