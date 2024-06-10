package com.sat.chat.ui.message.internal

import com.sat.chat.application.command.OnlineRecorder
import com.sat.chat.domain.ChatRoomDeletedEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent

val log = KotlinLogging.logger { }

@Component
class WebSocketEventHandler(
    private val onlineRecorder: OnlineRecorder,
) {

    @EventListener
    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        onlineRecorder.exit(event.sessionId)
    }

    @EventListener
    fun deleteRoom(event: ChatRoomDeletedEvent) {
        val chatTopicId = "/topic/rooms/${event.chatRoomId}/active-users"
        onlineRecorder.deleteChatRoom(chatTopicId)
    }
}
