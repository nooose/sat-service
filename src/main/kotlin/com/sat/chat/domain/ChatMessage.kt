package com.sat.chat.domain

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chat_message")
class ChatMessage(
    val senderId: Long,
    val content: String,
    val createdDateTime: LocalDateTime = LocalDateTime.now(),

    @DBRef
    val chatRoom: ChatRoom,
    @Id
    val id: ObjectId = ObjectId(),
)
