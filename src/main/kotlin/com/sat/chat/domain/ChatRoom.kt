package com.sat.chat.domain

import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

private const val MIN = 2
private const val MAX = 20

@Document(collection = "chat_room")
class ChatRoom(
    val name: String,
    val maximumCapacity: Int,
    val ownerId: Long,
    @Id
    val id: ObjectId = ObjectId()
) {
    init {
        require(name.isNotBlank()) { "채팅방 이름은 필수입니다." }
        require(maximumCapacity in MIN..MAX) { "최대 인원수는 $MIN ~ $MAX 명 입니다" }
    }

    fun isOwner(principalId: Long): Boolean {
        return ownerId == principalId
    }
}
