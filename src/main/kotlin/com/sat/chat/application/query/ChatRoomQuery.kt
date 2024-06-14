package com.sat.chat.application.query

import com.sat.chat.domain.ChatRoom

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
