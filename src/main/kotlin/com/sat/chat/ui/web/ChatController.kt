package com.sat.chat.ui.web

import com.sat.chat.application.command.OnlineRecorder
import com.sat.chat.domain.ChatMember
import com.sat.chat.domain.dto.query.ChatRoomOccupancyQuery
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

    /**
     * 채팅방 메시지 전송 처리
     */
    @MessageMapping("/chat/rooms/{chatRoomId}")
    @SendTo("/topic/rooms/{chatRoomId}")
    fun message(
        @DestinationVariable chatRoomId: String,
        @Payload message: ChatMessageRequest,
        principal: OAuth2AuthenticationToken,
    ): ChatMessageResponse {
        val member = principal.principal as AuthenticatedMember
        return ChatMessageResponse(member.id, member.name, message.text, LocalDateTime.now())
    }

    /**
     * 채팅방 구독 이벤트 처리
     */
    @SubscribeMapping("/topic/rooms/{roomId}/active-users")
    @SendTo("/topic/rooms/{roomId}/active-users")
    fun handleSubscription(
        @DestinationVariable roomId: String,
        principal: OAuth2AuthenticationToken,
        accessor: StompHeaderAccessor,
    ): Set<ChatMember> {
        return onlineRecorder.add(accessor.destination!!, ChatMember(accessor.sessionId!!, principal.name))
    }

    /**
     * 채팅 대기방 구독 이벤트 처리
     */
    @SubscribeMapping("/topic/rooms")
    @SendTo("/topic/rooms")
    fun handleWaitingSubscription(
        principal: OAuth2AuthenticationToken,
        accessor: StompHeaderAccessor,
    ): List<ChatRoomOccupancyQuery> {
        return onlineRecorder.getChatRoomOccupancy()
    }
}
