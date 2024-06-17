package com.sat.chat.config

import com.sat.chat.domain.ChatRoomRepository
import com.sat.chat.domain.ChatSessionRepository
import org.bson.types.ObjectId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component

@Component
class CapacityInterceptor(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatSessionRepository: ChatSessionRepository,
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        if (!message.isSubscribeChatRoom) {
            return message
        }

        val chatRoomId = message.destination.split("/").last()
        val chatRoom = chatRoomRepository.findByIdOrNull(ObjectId(chatRoomId))
            ?: throw IllegalStateException("채팅방을 찾을 수 없습니다. - $chatRoomId")
        val onlineMembers = chatSessionRepository.findAllByTopicId(message.destination)
        check(chatRoom.maximumCapacity > onlineMembers.size) { "채팅방이 꽉차있습니다. - ${message.destination}" }
        return message
    }

}

private val Message<*>.isSubscribeChatRoom: Boolean
    get() = headers["stompCommand"] == StompCommand.SUBSCRIBE &&
        destination.startsWith("/topic/rooms/") && !destination.endsWith("active-users")

private val Message<*>.destination: String
    get() = this.headers["simpDestination"] as String
