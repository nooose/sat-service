package com.sat.chat.application.command

import org.springframework.messaging.simp.SimpMessagingTemplate
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

    fun getOnlineMembers(topicId: String): Set<ChatMember> {
        return topicMap[topicId] ?: emptySet()
    }
}
