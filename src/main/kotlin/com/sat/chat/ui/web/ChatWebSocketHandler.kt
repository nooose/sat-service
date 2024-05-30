package com.sat.chat.ui.web

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

private val log = KotlinLogging.logger {}

class ChatWebSocketHandler : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        log.info { "afterConnectionEstablished" }
        super.afterConnectionEstablished(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        log.info { "afterConnectionClosed" }
        super.afterConnectionClosed(session, status)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        log.info { "handleTextMessage" }
        super.handleTextMessage(session, message)
    }

}
