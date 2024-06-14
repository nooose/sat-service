package com.sat.chat.domain

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

import java.time.LocalDateTime

interface ChatMessageRepository : MongoRepository<ChatMessage, String> {
    @Query("{'createdDateTime': { '\$gte': ?0 }, 'chatRoom.id': ?1 }")
    fun getMessages(beforeTime: LocalDateTime, roomId: ObjectId): List<ChatMessage>
}
