package com.sat.chat.application.command

import jakarta.validation.constraints.Size

data class ChatMessageCommand(
    @field:Size(max = 1000)
    val message: String
)
