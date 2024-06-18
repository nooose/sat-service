package com.sat.chat.command.domain

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class ChatSessionRepository {
    private val _sessions: MutableMap<String, MutableSet<ChatMember>> = ConcurrentHashMap()
    private val _sessionTopicMap: MutableMap<ChatMember, String> = ConcurrentHashMap()

    fun save(topicId: String, chatMember: ChatMember): List<ChatMember> {
        return _sessions.getOrPut(topicId) { mutableSetOf(chatMember) }
            .apply {
                _sessionTopicMap[chatMember] = topicId
                this.add(chatMember)
            }.toList()
    }

    fun deleteSession(chatMember: ChatMember): String? {
        val topicId = _sessionTopicMap.remove(chatMember) ?: return null
        _sessions[topicId]?.remove(chatMember)
        return topicId
    }

    fun findAllByTopicId(topicId: String): List<ChatMember> {
        return _sessions[topicId]?.toList() ?: emptyList()
    }

    fun findAll(): Map<String, MutableSet<ChatMember>> {
        return _sessions.toMap()
    }
}
