package com.sat.chat.application.query

import com.sat.chat.application.command.SEARCH_CONDITION_MINUTE
import com.sat.chat.domain.ChatMessageRepository
import com.sat.chat.domain.ChatRoomRepository
import com.sat.user.domain.port.repository.MemberRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class ChatQueryService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository,
    private val memberRepository: MemberRepository,
) {
    fun findChatRooms(): List<ChatRoomQuery> {
        return chatRoomRepository.findAll().map { chatRoom -> ChatRoomQuery.from(chatRoom) }
    }

    fun getMessages(roomId: String): List<ChatMessageQuery> {
        val now = LocalDateTime.now(ZoneOffset.UTC)
        val timeCondition = now.minusMinutes(SEARCH_CONDITION_MINUTE)
        val messages = chatMessageRepository.getMessages(timeCondition, ObjectId(roomId))

        val memberIds = messages.map { it.senderId }.distinct()
        val members = memberRepository.findAllById(memberIds)
        val memberMap = members.associateBy { member -> member.id }

        return messages.map {
            val member = memberMap[it.senderId]!!
            ChatMessageQuery.from(it, member.name)
        }
    }
}
