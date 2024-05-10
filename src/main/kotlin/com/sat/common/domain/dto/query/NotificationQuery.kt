package com.sat.common.domain.dto.query

data class NotificationQuery(
    val title: String,
    val body: String,
) {

    companion object {
        fun connect(): NotificationQuery {
            return NotificationQuery("SSE Connect", "connected")
        }
    }
}
