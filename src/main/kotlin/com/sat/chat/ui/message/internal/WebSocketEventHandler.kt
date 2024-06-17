package com.sat.chat.ui.message.internal

import com.sat.chat.application.command.ChatOnlineService
import com.sat.chat.domain.ChatMember
import com.sat.chat.domain.ChatRoomDeletedEvent
import com.sat.chat.domain.ChatSessionRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import org.springframework.web.socket.messaging.SessionSubscribeEvent

val log = KotlinLogging.logger { }

@Component
class WebSocketEventHandler(
    private val chatOnlineService: ChatOnlineService,
    private val chatSessionRepository: ChatSessionRepository,
    private val messagingTemplate: SimpMessagingTemplate,
) {

    @EventListener
    fun handleSubscribe(event: SessionSubscribeEvent) {
        val topicId = event.message.headers["simpDestination"] as String
        val sessionId = event.message.headers["simpSessionId"] as String
        val user = event.message.headers["simpUser"] as OAuth2AuthenticationToken

        if (topicId.matches("^/topic/rooms/[a-zA-Z0-9\\-]+".toRegex())) {
            chatSessionRepository.save(topicId, ChatMember(sessionId, user.name))
            val findOnlineCounts = chatOnlineService.findOnlineCounts()
            messagingTemplate.convertAndSend("/topic/rooms", findOnlineCounts)
        }
    }

    @EventListener
    fun handleDisconnect(event: SessionDisconnectEvent) {
        val topicId = chatSessionRepository.deleteSession(ChatMember(event.sessionId)) ?: return
        val onlineMembers = chatOnlineService.findOnline(topicId)
        messagingTemplate.convertAndSend("${topicId}/active-users", onlineMembers)
        val findOnlineCounts = chatOnlineService.findOnlineCounts()
        messagingTemplate.convertAndSend("/topic/rooms", findOnlineCounts)
    }

    @EventListener
    fun deleteRoom(event: ChatRoomDeletedEvent) {
        val chatTopicId = "/topic/rooms/${event.chatRoomId}/active-users"
        chatSessionRepository.findAllByTopicId(chatTopicId)
            .map { disconnectedCommand(it.sessionId) }
            .forEach {
                messagingTemplate.convertAndSend(chatTopicId, it)
            }
    }

    private fun disconnectedCommand(sessionId: String): StompHeaderAccessor {
        val accessor = StompHeaderAccessor.create(StompCommand.DISCONNECT)
        accessor.sessionId = sessionId
        accessor.setLeaveMutable(true)
        return accessor
    }
}
