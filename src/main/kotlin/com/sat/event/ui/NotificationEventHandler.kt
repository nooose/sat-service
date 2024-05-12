package com.sat.event.ui

import com.sat.event.application.NotificationProcessor
import com.sat.event.domain.NotificationEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener


@Component
class NotificationEventHandler(
    private val processor: NotificationProcessor,
) {

    @Async
    @TransactionalEventListener(NotificationEvent::class)
    fun <T : Any> handle(event: NotificationEvent<T>) {
        processor.send(event.targetMemberId, event.notification)
    }
}
