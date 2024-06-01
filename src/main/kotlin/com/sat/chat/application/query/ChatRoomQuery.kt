package com.sat.chat.application.query

import com.sat.chat.domain.ChatRoom

data class ChatRoomQuery(
    val id: String,
    val name: String,
    val ownerId: Long,
) {
    companion object {
        fun from(chatRoom: ChatRoom): ChatRoomQuery {
            return ChatRoomQuery(
                id = chatRoom.id!!,
                name = chatRoom.name,
                ownerId = chatRoom.ownerId,
            )
        }
    }
}
