package com.sat.chat.ui.web

import java.time.LocalDateTime

data class ChatMessageRequest(
    val text: String,
)

data class ChatMessageResponse(
    val senderId: Long,
    val senderName: String,
    val text: String,
    val createdDateTime: LocalDateTime,
)
