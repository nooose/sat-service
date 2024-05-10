package com.sat.common.application

data class NotificationDto(
    val title: String,
    val body: String = "",
) {

    companion object {
        fun connect(): NotificationDto {
            return NotificationDto("SSE Connect", "connected")
        }
    }
}
