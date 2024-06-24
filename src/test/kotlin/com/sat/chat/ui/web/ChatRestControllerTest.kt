package com.sat.chat.ui.web

import com.ninjasquad.springmockk.MockkBean
import com.sat.chat.command.application.ChatRoomCreateCommand
import com.sat.chat.command.application.ChatService
import com.sat.chat.query.ChatMessageQuery
import com.sat.chat.query.ChatQueryService
import com.sat.chat.query.ChatRoomQuery
import com.sat.chat.command.domain.ChatMessage
import com.sat.common.documentation.Documentation
import com.sat.common.documentation.dsl.DELETE
import com.sat.common.documentation.dsl.GET
import com.sat.common.documentation.dsl.POST
import com.sat.common.documentation.dsl.andDocument
import com.sat.common.security.WithAuthenticatedUser
import io.mockk.every
import org.bson.types.ObjectId
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@DisplayName(value = "API 문서화 - 채팅")
@WebMvcTest(ChatRestController::class)
class ChatRestControllerTest : Documentation() {

    @MockkBean(relaxUnitFun = true)
    private lateinit var chatService: ChatService

    @MockkBean
    private lateinit var chatQueryService: ChatQueryService

    @WithAuthenticatedUser
    @Test
    fun `채팅방 생성`() {
        every { chatService.createRoom(any(), any()) } returns UUID.randomUUID().toString()

        val request = ChatRoomCreateCommand("채팅방 A", 10)

        mockMvc.POST("/chat/rooms") {
            jsonContent(request)
        }.andExpect {
            status { isCreated() }
        }.andDocument {
            tag = "채팅"
            summary = "채팅방 생성"
            requestBody<ChatRoomCreateCommand> {
                field("name", "생성할 채팅방 이름")
                field("maximumCapacity", "채팅방 최대 인원수")
            }
            responseHeaders {
                header(HttpHeaders.LOCATION, "채팅방 리소스 위치")
            }
        }
    }

    @Test
    fun `채팅방 목록 조회`() {
        every { chatQueryService.findChatRooms() } returns listOf(
            ChatRoomQuery(UUID.randomUUID().toString(), "채팅방 A", 5, 1L),
            ChatRoomQuery(UUID.randomUUID().toString(), "채팅방 B", 10, 2L)
        )

        mockMvc.GET("/chat/rooms") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "채팅"
            summary = "채팅방 목록 조회"
            responseBody<ChatRoomQuery> {
                field("[].id", "채팅방 ID")
                field("[].name", "채팅방 이름")
                field("[].ownerId", "채팅방 생성자 ID")
                field("[].maximumCapacity", "채팅방 최대 인원수")
            }
        }
    }

    @Test
    fun `채팅방 메시지 조회`() {
        every { chatQueryService.getMessages(any(), any()) } returns
                listOf(
                    ChatMessageQuery(1, "정순이", "멍멍", LocalDateTime.now(ZoneOffset.UTC)),
                    ChatMessageQuery(1, "정순이", "왈왈", LocalDateTime.now(ZoneOffset.UTC))
                )

        mockMvc.GET("/chat/rooms/{roomId}/messages", ObjectId().toString()) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "채팅"
            summary = "채팅방 메시지 조회"
            responseBody<List<ChatMessage>> {
                field("[].senderId", "발신자 ID")
                field("[].senderName", "발신자 이름")
                field("[].content", "메시지")
                field("[].createdDateTime", "메시지 발송 시간")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `채팅방 삭제`() {
        mockMvc.DELETE("/chat/rooms/{roomId}", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "채팅"
            summary = "채팅방 삭제"
        }
    }
}
