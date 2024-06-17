package com.sat.chat.ui.web

import com.sat.chat.application.command.ChatMessageCommand
import com.sat.chat.application.command.ChatOnlineService
import com.sat.chat.application.command.ChatService
import com.sat.chat.domain.ChatMember
import com.sat.chat.domain.dto.query.ChatRoomOccupancyQuery
import com.sat.common.config.security.AuthenticatedMember
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Controller
import java.time.LocalDateTime

private val log = KotlinLogging.logger { }

@Controller
class ChatController(
    private val chatService: ChatService,
    private val chatOnlineService: ChatOnlineService,
    private val messagingTemplate: SimpMessagingTemplate,
) {

    /**
     * 채팅방 메시지 전송 처리
     */
    @MessageMapping("/chat/rooms/{chatRoomId}")
    @SendTo("/topic/rooms/{chatRoomId}")
    fun message(
        @DestinationVariable chatRoomId: String,
        @Valid @Payload message: ChatMessageRequest,
        principal: OAuth2AuthenticationToken,
    ): ChatMessageResponse {
        val member = principal.principal as AuthenticatedMember
        chatService.saveMessage(chatRoomId, ChatMessageCommand(member.id, message.content))
        return ChatMessageResponse(member.id, member.name, message.content, LocalDateTime.now())
    }

    /**
     * 채팅방 구독 이벤트 처리
     */
    @SubscribeMapping("/topic/rooms/{roomId}")
    fun handleChatRoomSubscription(
        @DestinationVariable roomId: String,
        principal: OAuth2AuthenticationToken,
        accessor: StompHeaderAccessor,
    ) {
        messagingTemplate.convertAndSend("/topic/rooms/${roomId}/active-users", chatOnlineService.findOnline("/topic/rooms/${roomId}"))
        messagingTemplate.convertAndSend("/topic/rooms",  chatOnlineService.findOnlineCounts())
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
    ): List<ChatMember> {
        return chatOnlineService.findOnline("/topic/rooms/${roomId}")
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
        return chatOnlineService.findOnlineCounts()
    }
}
