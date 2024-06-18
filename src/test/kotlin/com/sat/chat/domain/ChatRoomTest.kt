package com.sat.chat.domain

import com.sat.chat.command.domain.ChatRoom
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import org.bson.types.ObjectId

@DisplayName("도메인 - 채팅방")
class ChatRoomTest : StringSpec({

    "ObjectId를 생성하면 문자열로 변환할 수 있다" {
        val objectId = ObjectId()
        println(objectId.toString())
        println(objectId.toHexString())
        objectId.toString().shouldNotBeNull()
    }

    "채팅방 생성 시 최소 인원을 충족해야 한다." {
        shouldThrow<IllegalArgumentException> {
            ChatRoom("채팅방", 1, 1L)
        }
    }

    "채팅방 생성 시 공백 이름으로 만들 수 없다." {
        shouldThrow<IllegalArgumentException> {
            ChatRoom("", 10, 1L)
        }
    }
})
