package com.sat.chat.ui.web

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class ChatMessageRequest(
    @field:Size(max = 1000)
    @field:NotBlank
    val text: String,
)

data class ChatMessageResponse(
    val senderId: Long,
    val senderName: String,
    val text: String,
    val createdDateTime: LocalDateTime,
)
