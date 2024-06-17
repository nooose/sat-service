package com.sat.chat.infrastructure

import com.sat.chat.domain.MessageClient
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component

@Component
class WebSocketMessageClient(
    private val simpleMessagingTemplate: SimpMessagingTemplate,
) : MessageClient {

    override fun publish(destination: String, payload: Any) {
        simpleMessagingTemplate.convertAndSend(destination, payload)
    }

    override fun exit(destination: String, sessionId: String) {
        simpleMessagingTemplate.convertAndSend(destination, disconnectedCommand(sessionId))
    }

    private fun disconnectedCommand(sessionId: String): StompHeaderAccessor {
        val accessor = StompHeaderAccessor.create(StompCommand.DISCONNECT)
        accessor.sessionId = sessionId
        accessor.setLeaveMutable(true)
        return accessor
    }
}
