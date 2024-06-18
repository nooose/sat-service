package com.sat.chat.command.application

import jakarta.validation.constraints.Size

data class ChatRoomCreateCommand(
    @field:Size(min = 2, max = 20)
    val name: String,
    val maximumCapacity: Int,
)

data class ChatMessageCommand(
    val senderId: Long,
    @field:Size(max = 1000)
    val message: String,
)
