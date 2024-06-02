package com.sat.chat.application.command

import com.sat.chat.ui.message.internal.log
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class OnlineRecorder {
    private val topicMap: ConcurrentHashMap<String, MutableSet<ChatUser>> = ConcurrentHashMap()
    private val sessionMap: ConcurrentHashMap<String, String> = ConcurrentHashMap()

    fun add(topicId: String, user: ChatUser) {
        log.info { user.sessionId + " 참여완료" }
        sessionMap[user.sessionId] = topicId
        if (topicMap[topicId] == null) {
            topicMap[topicId] = mutableSetOf(user)
            return
        }
        topicMap[topicId]!!.add(user)
    }

    fun exit(sessionId: String) {
        val topicId = sessionMap[sessionId]
        topicMap[topicId]!!.removeIf { it.sessionId == sessionId }
        sessionMap.remove(sessionId)
    }

    fun findTopicIdBySessionId(sessionId: String): String {
        return sessionMap[sessionId]!!
    }

    fun getOnlineUsers(topicId: String): Set<ChatUser> {
        return topicMap[topicId] ?: emptySet()
    }
}

data class ChatUser(
    val sessionId: String,
    val name: String,
)
