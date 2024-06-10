package com.sat.chat.application.command

import com.sat.chat.domain.*
import com.sat.event.utils.Events
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
) {

    fun createRoom(command: ChatRoomCreateCommand, principalId: Long): ChatRoom {
        return chatRoomRepository.save(ChatRoom(command.name, command.maximumCapacity, principalId))
    }

    @Async
    fun saveMessage(roomId: String, command: ChatMessageCommand) {
        val chatRoom = chatRoomRepository.findByIdOrNull(roomId) ?: throw IllegalStateException("채팅방을 찾을 수 없습니다. - $roomId")
        val chatMessage = ChatMessage(command.senderId, command.message, LocalDateTime.now(), chatRoom)
        chatMessageRepository.save(chatMessage)
    }

    fun deleteRoom(roomId: String) {
        chatRoomRepository.deleteById(roomId)
        Events.publish(ChatRoomDeletedEvent(roomId))
    }
}
