package com.sat.common.application

data class NotificationDto<T>(
    val title: String,
    val body: String = "",
    val data: T? = null,
) {

    companion object {
        fun connect(): NotificationDto<Unit> {
            return NotificationDto("SSE Connect", "connected")
        }
    }
}
