package com.sat.chat.ui.web

import com.sat.chat.application.command.ChatRoomCreateCommand
import com.sat.chat.application.command.ChatService
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest


@RestController
class ChatRestController(
    private val chatService: ChatService,
) {

    @PostMapping("/chat/rooms")
    fun createChatRoom(
        @LoginMember member: AuthenticatedMember,
        @Valid @RequestBody command: ChatRoomCreateCommand,
    ): ResponseEntity<Unit> {
        val room = chatService.createRoom(command, member.id)
        val uri = fromCurrentRequest()
            .path("/chat/rooms/${room.id}")
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }
}

