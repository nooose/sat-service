package com.sat.chat.query

import com.sat.chat.command.domain.ChatMessage
import com.sat.chat.command.domain.ChatRoom
import java.time.LocalDateTime

data class ChatRoomQuery(
    val id: String,
    val name: String,
    val maximumCapacity: Int,
    val ownerId: Long,
) {
    companion object {
        fun from(chatRoom: ChatRoom): ChatRoomQuery {
            return ChatRoomQuery(
                id = chatRoom.id.toString(),
                name = chatRoom.name,
                maximumCapacity = chatRoom.maximumCapacity,
                ownerId = chatRoom.ownerId,
            )
        }
    }
}

data class ChatMessageQuery(
    val senderId: Long,
    val senderName: String,
    val content: String,
    val createdDateTime: LocalDateTime,
) {

    companion object {
        fun from(chatMessage: ChatMessage, memberName: String): ChatMessageQuery {
            return ChatMessageQuery(
                senderId = chatMessage.senderId,
                senderName = memberName,
                content = chatMessage.content,
                createdDateTime = chatMessage.createdDateTime,
            )
        }
    }
}

data class ChatRoomOccupancyQuery(
    val id: String,
    val onlineMemberCount: Int,
)
