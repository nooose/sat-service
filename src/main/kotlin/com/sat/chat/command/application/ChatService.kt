package com.sat.chat.command.application

import com.sat.chat.command.domain.*
import com.sat.common.domain.exception.NotFoundException
import com.sat.event.utils.Events
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ChatService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
) {
    /**
     * @return 채팅방 ID
     */
    fun createRoom(command: ChatRoomCreateCommand, principalId: Long): String {
        val chatRoom = ChatRoom(command.name, command.maximumCapacity, principalId)
        chatRoomRepository.save(chatRoom)
        return chatRoom.id.toString()
    }

    @Async
    fun saveMessage(roomId: String, command: ChatMessageCommand) {
        val chatRoom = chatRoomRepository.findByIdOrNull(ObjectId(roomId)) ?: throw IllegalStateException("채팅방을 찾을 수 없습니다. - $roomId")
        val chatMessage = ChatMessage(command.senderId, command.message, LocalDateTime.now(), chatRoom)
        chatMessageRepository.save(chatMessage)
    }

    fun deleteRoom(roomId: String, principalId: Long) {
        val objectId = ObjectId(roomId)
        val chatRoom = chatRoomRepository.findByIdOrNull(objectId) ?: throw NotFoundException("채팅방을 찾을 수 없습니다. - $roomId")
        check(chatRoom.isOwner(principalId))
        chatRoomRepository.deleteById(objectId)
        Events.publish(ChatRoomDeletedEvent(roomId))
    }
}
