package com.sat.chat.ui.web

import com.sat.chat.application.command.ChatRoomCreateCommand
import com.sat.chat.application.command.ChatService
import com.sat.chat.application.query.ChatQueryService
import com.sat.chat.application.query.ChatRoomQuery
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest


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
        val uri = fromCurrentRequest()
            .path("/chat/rooms/${roomId}")
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/chat/rooms")
    fun findChatRooms(): List<ChatRoomQuery> {
        return chatQueryService.findChatRooms()
    }

    @DeleteMapping("/chat/rooms/{roomId}")
    fun deleteRoom(
        @PathVariable roomId: String,
        @LoginMember member: AuthenticatedMember,
    ) {
        return chatService.deleteRoom(roomId, member.id)
    }
}

