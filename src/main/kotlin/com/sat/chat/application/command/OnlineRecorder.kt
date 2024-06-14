package com.sat.chat.application.command

import com.sat.chat.domain.ChatMember
import com.sat.chat.domain.dto.query.ChatRoomOccupancyQuery
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

data class ChatRoomTopic(
    val chatRoomId: String,
    val topicId: String,
)

@Service
class OnlineRecorder(
    private val messageSendingTemplate: SimpMessagingTemplate,
) {
    private val _topicMap: MutableMap<ChatRoomTopic, MutableSet<ChatMember>> = ConcurrentHashMap()
    private val _sessionMap: MutableMap<String, ChatRoomTopic> = ConcurrentHashMap()

    val topicMap
        get() = _topicMap.toMap()
    val sessionMap
        get() = _sessionMap.toMap()

    fun add(topic: ChatRoomTopic, user: ChatMember): Set<ChatMember> {
        _sessionMap[user.sessionId] = topic
        val members = _topicMap.getOrPut(topic) { mutableSetOf(user) }
            .apply { this.add(user) }
        messageSendingTemplate.convertAndSend("/topic/rooms", getChatRoomOccupancy())
        return members
    }

    fun exit(sessionId: String) {
        val topic = _sessionMap[sessionId] ?: return
        _topicMap[topic]!!.removeIf { it.sessionId == sessionId }
        _sessionMap.remove(sessionId)!!
        messageSendingTemplate.convertAndSend(topic.topicId, getOnlineMembers(topic))
        messageSendingTemplate.convertAndSend("/topic/rooms", getChatRoomOccupancy())
    }

    fun deleteChatRoom(topic: ChatRoomTopic) {
        getOnlineMembers(topic).map { disconnectedCommand(it.sessionId) }
                .forEach {
                    messageSendingTemplate.convertAndSend(topic.topicId, it)
                    _sessionMap.remove(it.sessionId)
                }
        _topicMap.remove(topic)
    }

    private fun getOnlineMembers(topic: ChatRoomTopic): Set<ChatMember> {
        return _topicMap[topic] ?: emptySet()
    }

    private fun disconnectedCommand(sessionId: String): StompHeaderAccessor {
        val accessor = StompHeaderAccessor.create(StompCommand.DISCONNECT)
        accessor.sessionId = sessionId
        accessor.setLeaveMutable(true)
        return accessor
    }

    fun getChatRoomOccupancy(): List<ChatRoomOccupancyQuery> {
        return _topicMap.map { (key, value) -> ChatRoomOccupancyQuery(key.chatRoomId, value.size) }
    }
}
