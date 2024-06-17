package com.sat.chat.ui.message.internal

import com.sat.chat.application.command.ChatOnlineService
import com.sat.chat.domain.ChatMember
import com.sat.chat.domain.ChatRoomDeletedEvent
import com.sat.chat.domain.ChatSessionRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

val log = KotlinLogging.logger { }

@Component
class WebSocketEventHandler(
    private val chatOnlineService: ChatOnlineService,
    private val chatSessionRepository: ChatSessionRepository,
) {

    @EventListener
    fun handleDisconnect(event: SessionDisconnectEvent) {
        val topicId = chatSessionRepository.deleteSession(ChatMember(event.sessionId)) ?: return
        chatOnlineService.sendOnlineCounts()
        chatOnlineService.sendActiveUsers(topicId.split("/").last())
    }

    @EventListener
    fun deleteRoom(event: ChatRoomDeletedEvent) {
        val chatTopicId = "/topic/rooms/${event.chatRoomId}"
        chatSessionRepository.findAllByTopicId(chatTopicId)
            .forEach { chatOnlineService.exit(chatTopicId, it.sessionId) }
    }
}
