package com.sat.chat.application.command

import com.sat.chat.domain.ChatMember
import com.sat.chat.domain.ChatSessionRepository
import com.sat.chat.domain.dto.query.ChatRoomOccupancyQuery
import org.springframework.stereotype.Service

@Service
class ChatOnlineService(
    private val repository: ChatSessionRepository,
) {

    fun findOnlineCounts(): List<ChatRoomOccupancyQuery> {
        return repository.findAll()
            .map {
                val chatRoomId = it.key.split("/").last()
                val onlineCount = it.value.size
                ChatRoomOccupancyQuery(chatRoomId, onlineCount)
            }
    }

    fun findOnline(topicId: String): List<ChatMember> {
        return repository.findAllByTopicId(topicId)
    }
}
