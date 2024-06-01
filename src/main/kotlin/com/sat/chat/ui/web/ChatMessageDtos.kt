package com.sat.chat.ui.web

import java.time.LocalDateTime

data class ChatMessageRequest(
    val senderId: String,
    val text: String,
)

data class ChatMessageResponse(
    val senderId: String,
    val text: String,
    val createdDateTime: LocalDateTime,
)
