package com.sat.chat.ui.message.internal

import com.sat.chat.application.command.OnlineRecorder
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

val log = KotlinLogging.logger { }

@Component
class WebSocketEventHandler(
    private val onlineRecorder: OnlineRecorder,
    private val messageSimpMessagingTemplate: SimpMessagingTemplate,
) {

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        log.info { "퇴장: ${event.user?.name}" }
        val topicId = onlineRecorder.findTopicIdBySessionId(event.sessionId)
        onlineRecorder.exit(event.sessionId)
        messageSimpMessagingTemplate.convertAndSend(topicId, onlineRecorder.getOnlineUsers(topicId))
    }
}
