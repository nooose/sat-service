package com.sat.chat.application.command

import com.sat.chat.domain.ChatMember
import com.sat.chat.domain.ChatSessionRepository
import com.sat.chat.domain.MessageClient
import com.sat.chat.domain.dto.query.ChatRoomOccupancyQuery
import org.springframework.stereotype.Service

@Service
class ChatOnlineService(
    private val repository: ChatSessionRepository,
    private val messageClient: MessageClient,
) {

    fun record(roomId: String, chatMember: ChatMember) {
        repository.save("/topic/rooms/${roomId}", chatMember)
        sendActiveUsers(roomId)
        sendOnlineCounts()
    }

    fun sendActiveUsers(roomId: String) {
        val roomActiveMembers = repository.findAllByTopicId("/topic/rooms/${roomId}")
        messageClient.publish("/topic/rooms/${roomId}/active-users", roomActiveMembers)
    }

    fun sendOnlineCounts() {
        val onlineCounts = repository.findAll()
            .map {
                val chatRoomId = it.key.split("/").last()
                val onlineCount = it.value.size
                ChatRoomOccupancyQuery(chatRoomId, onlineCount)
            }
        messageClient.publish("/topic/rooms", onlineCounts)
    }

    fun exit(topicId: String, sessionId: String) {
        messageClient.exit(topicId, sessionId)
    }
}
