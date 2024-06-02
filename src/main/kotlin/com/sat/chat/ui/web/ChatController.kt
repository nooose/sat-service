package com.sat.chat.ui.web

import com.sat.chat.application.command.ChatUser
import com.sat.chat.application.command.OnlineRecorder
import com.sat.common.config.security.AuthenticatedMember
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

private val log = KotlinLogging.logger { }

@Controller
class ChatController(
    private val onlineRecorder: OnlineRecorder,
) {

    @MessageMapping("/chat/rooms/{chatRoomId}")
    @SendTo("/topic/rooms/{chatRoomId}")
    fun message(
        @DestinationVariable chatRoomId: String,
        @Payload message: ChatMessageRequest,
        principal: OAuth2AuthenticationToken,
    ): ChatMessageResponse {
        val member = principal.principal as AuthenticatedMember
        log.info { "[${chatRoomId}] 수신: $message" }
        return ChatMessageResponse(member.id, member.name, message.text, LocalDateTime.now())
    }

    /**
     * 채팅방 구독 이벤트
     */
    @SubscribeMapping("/topic/rooms/{roomId}/active-users")
    @SendTo("/topic/rooms/{roomId}/active-users")
    fun handleSubscription(
        @DestinationVariable roomId: String,
        principal: OAuth2AuthenticationToken,
        accessor: StompHeaderAccessor,
    ): Set<ChatUser> {
        onlineRecorder.add(accessor.destination!!, ChatUser(accessor.sessionId!!, principal.name))
        log.info { "[$roomId] 입장: ${principal.name}" }
        return onlineRecorder.getOnlineUsers(accessor.destination!!)
    }
}
