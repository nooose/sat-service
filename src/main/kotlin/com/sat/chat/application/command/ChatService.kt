package com.sat.chat.application.command

import com.sat.chat.domain.ChatMessage
import com.sat.chat.domain.ChatMessageRepository
import com.sat.chat.domain.ChatRoom
import com.sat.chat.domain.ChatRoomRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
) {

    fun createRoom(command: ChatRoomCreateCommand, principalId: Long): ChatRoom {
        return chatRoomRepository.save(ChatRoom(command.name, principalId))
    }

    fun saveMessage(roomId: String, command: ChatMessageCommand, principalId: Long) {
        val chatRoom = chatRoomRepository.findByIdOrNull(roomId) ?: throw IllegalStateException("채팅방을 찾을 수 없습니다. - $roomId")
        val chatMessage = ChatMessage(principalId, command.message, LocalDateTime.now(), chatRoom)
        chatMessageRepository.save(chatMessage)
    }
}
