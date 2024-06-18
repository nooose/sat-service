package com.sat.chat.query

import com.sat.chat.command.domain.ChatMessageRepository
import com.sat.chat.command.domain.ChatRoomRepository
import com.sat.user.command.domain.member.MemberRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.LocalDateTime


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

    fun getMessages(roomId: String, baseTime: LocalDateTime): List<ChatMessageQuery> {
        val messages = chatMessageRepository.getMessages(baseTime, ObjectId(roomId))

        val memberIds = messages.map { it.senderId }.distinct()
        val members = memberRepository.findAllById(memberIds)
        val memberMap = members.associateBy(keySelector = { it.id }, valueTransform = { it.name })

        return messages.map {
            val memberName = memberMap[it.senderId]!!
            ChatMessageQuery.from(it, memberName)
        }
    }
}
