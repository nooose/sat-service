package com.sat.chat.application.command

import jakarta.validation.constraints.Size

data class ChatRoomCreateCommand(
    @field:Size(min = 2, max = 20)
    val name: String,
    val maximumCapacity: Int,
)
