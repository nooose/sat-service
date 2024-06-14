package com.sat.chat.application.command

import com.sat.chat.domain.ChatMember
import io.kotest.assertions.assertSoftly
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.maps.shouldNotHaveKey
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.util.*

@DisplayName("서비스 - 채팅방 온라인 기록 테스트")
class OnlineRecorderTest : BehaviorSpec({

    val messagingTemplate = mockk<SimpMessagingTemplate>()

    Given("채팅방이 있고") {
        every { messagingTemplate.convertAndSend(any(), any(Any::class)) } just runs
        val onlineRecorder = OnlineRecorder(messagingTemplate)

        val chatRoomId = UUID.randomUUID().toString()
        val topic = ChatRoomTopic(chatRoomId, "/topic/A")
        val chatMemberA = chatMemberFixture("권규정")
        val chatMemberB = chatMemberFixture("김영철")

        When("사용자들이 채팅방에 입장하면") {
            onlineRecorder.add(topic, chatMemberA)
            onlineRecorder.add(topic, chatMemberB)

            Then("실시간 인원을 확인할 수 있다.") {
                val online = onlineRecorder.getChatRoomOccupancy().first()
                online.onlineMemberCount shouldBe 2
            }
        }
    }

    Given("채팅방에 사용자가 있고") {
        every { messagingTemplate.convertAndSend(any(), any(Any::class)) } just runs

        val onlineRecorder = OnlineRecorder(messagingTemplate)

        val chatRoomId = UUID.randomUUID().toString()
        val topic = ChatRoomTopic(chatRoomId, "/topic/A")
        val chatMemberA = chatMemberFixture("권규정")
        val chatMemberB = chatMemberFixture("김영철")
        val chatMemberC = chatMemberFixture("박지수")

        onlineRecorder.add(topic, chatMemberA)
        onlineRecorder.add(topic, chatMemberB)
        onlineRecorder.add(topic, chatMemberC)

        When("특정 사용자가 채팅방을 나가면") {
            onlineRecorder.exit(chatMemberB.sessionId)
            onlineRecorder.exit(chatMemberC.sessionId)

            Then("실시간 인원이 줄어든다.") {
                val online = onlineRecorder.getChatRoomOccupancy().first()

                assertSoftly {
                    online.onlineMemberCount shouldBe 1
                    onlineRecorder.topicMap[topic]!!.shouldContainExactly(chatMemberA)
                    onlineRecorder.sessionMap shouldNotHaveKey chatMemberB.sessionId
                    onlineRecorder.sessionMap shouldNotHaveKey chatMemberC.sessionId
                }
            }
        }
    }
})

private fun chatMemberFixture(name: String): ChatMember {
    return ChatMember(UUID.randomUUID().toString(), name)
}
