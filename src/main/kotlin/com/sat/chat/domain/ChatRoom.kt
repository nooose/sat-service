package com.sat.chat.domain

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "chat_room")
class ChatRoom(
    val name: String,
    val ownerId: Long,
    @Id
    val id: String? = null,
)
