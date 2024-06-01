package com.sat.chat.ui.web

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

private val log = KotlinLogging.logger { }

@Controller
class ChatController(
    private val messagingTemplate: SimpMessagingTemplate,
) {

    @MessageMapping("/rooms/{chatRoomId}")
    fun message(
        @DestinationVariable("chatRoomId") chatRoomId: String,
        @Payload message: ChatMessageRequest
    ) {
        log.info { "[${chatRoomId}] 수신: $message" }
        messagingTemplate.convertAndSend(
            "/topic/rooms/${chatRoomId}",
            ChatMessageResponse(message.senderId, message.text, LocalDateTime.now()),
        )
    }
}
