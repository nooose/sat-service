package com.sat.chat.ui.web

import com.sat.chat.command.application.ChatRoomCreateCommand
import com.sat.chat.command.application.ChatService
import com.sat.chat.query.ChatMessageQuery
import com.sat.chat.query.ChatQueryService
import com.sat.chat.query.ChatRoomQuery
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri
import java.time.LocalDateTime
import java.time.ZoneOffset

private const val SEARCH_CONDITION_MINUTE: Long = 30

@RestController
class ChatRestController(
    private val chatService: ChatService,
    private val chatQueryService: ChatQueryService,
) {

    @PostMapping("/chat/rooms")
    fun createChatRoom(
        @LoginMember member: AuthenticatedMember,
        @Valid @RequestBody command: ChatRoomCreateCommand,
    ): ResponseEntity<Unit> {
        val roomId = chatService.createRoom(command, member.id)
        val uri = fromCurrentRequestUri()
            .path("/${roomId}")
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/chat/rooms")
    fun findChatRooms(): List<ChatRoomQuery> {
        return chatQueryService.findChatRooms()
    }

    @GetMapping("/chat/rooms/{roomId}/messages")
    fun getMessages(@PathVariable roomId: String): List<ChatMessageQuery> {
        val now = LocalDateTime.now(ZoneOffset.UTC)
        return chatQueryService.getMessages(roomId, now.minusMinutes(SEARCH_CONDITION_MINUTE))
    }

    @DeleteMapping("/chat/rooms/{roomId}")
    fun deleteRoom(
        @PathVariable roomId: String,
        @LoginMember member: AuthenticatedMember,
    ) {
        return chatService.deleteRoom(roomId, member.id)
    }
}

