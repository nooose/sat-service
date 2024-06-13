package com.sat.chat.domain

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ChatMessageRepository : MongoRepository<ChatMessage, String> {
    @Query("{'chatRoom.id':  ?0}")
    fun getMessages(roomId: String): List<ChatMessage>
}
