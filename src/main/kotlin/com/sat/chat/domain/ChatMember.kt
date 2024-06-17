package com.sat.chat.domain

data class ChatMember(
    val sessionId: String,
    val name: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ChatMember
        return sessionId == other.sessionId
    }

    override fun hashCode(): Int {
        return sessionId.hashCode()
    }
}
