package com.sat.chat.application.query

import com.sat.chat.domain.ChatRoomRepository
import org.springframework.stereotype.Service

@Service
class ChatQueryService(
    private val chatRoomRepository: ChatRoomRepository,
) {
    fun findChatRooms(): List<ChatRoomQuery> {
        return chatRoomRepository.findAll().map { chatRoom -> ChatRoomQuery.from(chatRoom) }
    }
}
