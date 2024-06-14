package com.sat.chat.application.query

import com.sat.chat.domain.ChatMessageRepository
import com.sat.chat.domain.ChatRoomRepository
import com.sat.user.domain.port.repository.MemberRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime

private const val SEARCH_CONDITION_MINUTE: Long = 30

@Service
class ChatQueryService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val memberRepository: MemberRepository,
) {
    fun findChatRooms(): List<ChatRoomQuery> {
        return chatRoomRepository.findAll()
            .map { chatRoom -> ChatRoomQuery.from(chatRoom) }
    }

    fun getMessages(roomId: String, now: LocalDateTime): List<ChatMessageQuery> {
        val timeCondition = now.minusMinutes(SEARCH_CONDITION_MINUTE)
        val messages = chatMessageRepository.getMessages(timeCondition, ObjectId(roomId))

        val memberIds = messages.map { it.senderId }.distinct()
        val members = memberRepository.findAllById(memberIds)
        val memberMap = members.associateBy(keySelector = { it.id }, valueTransform = { it.name })

        return messages.map {
            val memberName = memberMap[it.senderId]!!
            ChatMessageQuery.from(it, memberName)
        }
    }
}
