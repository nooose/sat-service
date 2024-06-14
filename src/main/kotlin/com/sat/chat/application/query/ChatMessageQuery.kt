package com.sat.chat.application.query

import com.sat.chat.domain.ChatMessage
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chat_message")
class ChatMessageQuery(
    val senderId: Long,
    val senderName: String,
    val content: String,
    val createdDateTime: LocalDateTime,
) {
    companion object {
        fun from(chatMessage: ChatMessage, memberName: String): ChatMessageQuery {
            return ChatMessageQuery(
                senderId = chatMessage.senderId,
                senderName = memberName,
                content = chatMessage.content,
                createdDateTime = chatMessage.createdDateTime,
            )
        }
    }
}
