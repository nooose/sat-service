package com.sat.board.ui.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.sat.board.application.CommentCommandService
import com.sat.board.application.CommentQueryService
import com.sat.board.application.dto.command.CommentCreateCommand
import com.sat.board.application.dto.command.CommentUpdateCommand
import com.sat.board.application.dto.query.CommentQuery
import com.sat.board.common.documentation.Documentation
import com.sat.board.common.documentation.dsl.*
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


@DisplayName(value = "API 문서화 - 댓글")
@WebMvcTest(CommentRestController::class)
class CommentRestControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
) : Documentation(){

    @MockkBean
    lateinit var commentCommandService: CommentCommandService

    @MockkBean
    lateinit var commentQueryService: CommentQueryService


    @Test
    fun `댓글 생성`() {
        val request = CommentCreateCommand("이 게시글은 정말 유익합니다.", 2L)

        every { commentCommandService.create(any(), any()) } just runs

        mockMvc.POST("/board/articles/{articleId}/comments", 1L) {
            content = objectMapper.writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
        }.andExpect {
            status { isCreated() }
        }.andDocument {
            tag = "게시판 > 댓글"
            summary = "댓글 생성"
            pathVariables {
                param("articleId", "게시글 ID")
            }
            requestBody {
                field("content", "댓글 내용")
                field("parentId", "댓글 부모 ID", optional = true)
            }
            responseHeaders {
                header(HttpHeaders.LOCATION, "게시글 URI")
            }
        }
    }

    @Test
    fun `댓글 조회`() {
        val response = listOf(
            CommentQuery(
                articleId = 1L,
                id = 1L,
                content = "유익한 게시글 입니다.",
                mutableListOf(
                    CommentQuery(
                        articleId = 1L,
                        id = 2L,
                        content = "유익한 부모 댓글 입니다.")
                )
            )
        )

        every { commentQueryService.get(any()) } returns response

        mockMvc.GET("/board/articles/{articleId}/comments", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 댓글"
            summary = "댓글 조회"
            responseBody {
                field("[].articleId", "게시판 ID")
                field("[].id", "댓글 ID")
                field("[].content", "댓글 내용")
                field("[].children[].articleId", "자식 게시판 ID")
                field("[].children[].id", "자식 ID")
                field("[].children[].content", "자식 댓글 내용")
            }
        }
    }

    @Test
    fun `댓글 수정`() {
        val request = CommentUpdateCommand("너무너무 재밌어요")

        every { commentCommandService.update(any(), any()) } just runs

        mockMvc.PUT("/board/comments/{id}", 2L) {
            content = objectMapper.writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 댓글"
            summary = "댓글 수정"
            pathVariables {
                param("id", "댓글 ID")
            }
            requestBody {
                field("content", "댓글 내용")
            }
        }
    }

    @Test
    fun `댓글 삭제`() {
        every { commentCommandService.delete(any()) } just runs

        mockMvc.DELETE("/board/comments/{id}", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 댓글"
            summary = "댓글 삭제"
            pathVariables {
                param("id", "댓글 ID")
            }
        }
    }
}

