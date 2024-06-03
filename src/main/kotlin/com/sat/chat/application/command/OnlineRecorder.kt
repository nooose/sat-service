package com.sat.chat.application.command

import com.sat.chat.domain.ChatMember
import com.sat.chat.domain.dto.query.ChatRoomOccupancyQuery
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap


@Service
class OnlineRecorder(
    private val messageSendingTemplate: SimpMessagingTemplate,
) {
    private val topicMap: ConcurrentHashMap<String, MutableSet<ChatMember>> = ConcurrentHashMap()
    private val sessionMap: ConcurrentHashMap<String, String> = ConcurrentHashMap()

    fun add(topicId: String, user: ChatMember): Set<ChatMember> {
        sessionMap[user.sessionId] = topicId
        return topicMap.getOrPut(topicId) { mutableSetOf(user) }
                .apply { this.add(user) }
    }

    fun exit(sessionId: String) {
        val topicId = remove(sessionId)
        messageSendingTemplate.convertAndSend(topicId, getOnlineMembers(topicId))
    }

    private fun remove(sessionId: String): String {
        val topicId = sessionMap[sessionId]!!
        topicMap[topicId]!!.removeIf { it.sessionId == sessionId }
        return sessionMap.remove(sessionId)!!
    }

    fun deleteChatRoom(chatRoomId: String) {
        val topicId = "/topic/rooms/${chatRoomId}/active-users"
        getOnlineMembers(topicId).map { disconnectedCommand(it.sessionId) }
                .forEach {
                    messageSendingTemplate.convertAndSend(topicId, it)
                    sessionMap.remove(it.sessionId)
                }
        topicMap.remove(topicId)
    }

    private fun getOnlineMembers(topicId: String): Set<ChatMember> {
        return topicMap[topicId] ?: emptySet()
    }

    private fun disconnectedCommand(sessionId: String): StompHeaderAccessor {
        val accessor = StompHeaderAccessor.create(StompCommand.DISCONNECT)
        accessor.sessionId = sessionId
        accessor.setLeaveMutable(true)
        return accessor
    }

    fun getChatRoomOccupancy(): List<ChatRoomOccupancyQuery> {
        return topicMap.map { (key, value) -> ChatRoomOccupancyQuery(key, value.size) }
    }
}
