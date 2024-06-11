package com.sat.chat.ui.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.sat.chat.application.command.ChatRoomCreateCommand
import com.sat.chat.application.command.ChatService
import com.sat.chat.application.query.ChatQueryService
import com.sat.chat.application.query.ChatRoomQuery
import com.sat.common.documentation.Documentation
import com.sat.common.documentation.dsl.DELETE
import com.sat.common.documentation.dsl.GET
import com.sat.common.documentation.dsl.POST
import com.sat.common.documentation.dsl.andDocument
import com.sat.common.security.WithAuthenticatedUser
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.util.*

@DisplayName(value = "API 문서화 - 채팅")
@WebMvcTest(ChatRestController::class)
class ChatRestControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
) : Documentation() {

    @MockkBean
    private lateinit var chatService: ChatService
    @MockkBean
    private lateinit var chatQueryService: ChatQueryService

    @WithAuthenticatedUser
    @Test
    fun `채팅방 생성`() {
        every { chatService.createRoom(any(), any()) } returns UUID.randomUUID().toString()

        mockMvc.POST("/chat/rooms") {
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
            content = objectMapper.writeValueAsString(ChatRoomCreateCommand("채팅방 A"))
        }.andExpect {
            status { isCreated() }
        }.andDocument {
            tag = "채팅"
            summary = "채팅방 생성"
            requestBody {
                type = ChatRoomCreateCommand::class
                field("name", "생성할 채팅방 이름")
            }
            responseHeaders {
                header(HttpHeaders.LOCATION, "채팅방 리소스 위치")
            }
        }
    }

    @Test
    fun `채팅방 목록 조회`() {
        every { chatQueryService.findChatRooms() } returns listOf(
                ChatRoomQuery(UUID.randomUUID().toString(), "채팅방 A", 1L),
                ChatRoomQuery(UUID.randomUUID().toString(), "채팅방 B", 2L)
        )

        mockMvc.GET("/chat/rooms") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "채팅"
            summary = "채팅방 목록 조회"
            responseBody {
                type = ChatRoomQuery::class
                field("[].id", "채팅방 ID")
                field("[].name", "채팅방 이름")
                field("[].ownerId", "채팅방 생성자 ID")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `채팅방 삭제`() {
        every { chatService.deleteRoom(any(), any()) } just runs

        mockMvc.DELETE("/chat/rooms/{roomId}", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "채팅"
            summary = "채팅방 삭제"
        }
    }
}
