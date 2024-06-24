package com.sat.board.ui.web

import com.ninjasquad.springmockk.MockkBean
import com.sat.board.command.application.CommentCommandService
import com.sat.board.command.application.CommentCreateCommand
import com.sat.board.command.application.CommentUpdateCommand
import com.sat.board.query.CommentQuery
import com.sat.board.query.CommentQueryService
import com.sat.common.documentation.Documentation
import com.sat.common.documentation.dsl.*
import com.sat.common.security.WithAuthenticatedUser
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders


@DisplayName(value = "API 문서화 - 댓글")
@WebMvcTest(CommentRestController::class)
class CommentRestControllerTest : Documentation(){

    @MockkBean(relaxUnitFun = true)
    lateinit var commentCommandService: CommentCommandService

    @MockkBean
    lateinit var commentQueryService: CommentQueryService


    @Test
    fun `댓글 생성`() {
        val request = CommentCreateCommand("이 게시글은 정말 유익합니다.", 2L)

        mockMvc.POST("/board/articles/{articleId}/comments", 1L) {
            jsonContent(request)
        }.andExpect {
            status { isCreated() }
        }.andDocument {
            tag = "게시판 > 댓글"
            summary = "댓글 생성"
            pathVariables {
                param("articleId", "게시글 ID")
            }
            requestBody<CommentCreateCommand> {
                field("content", "댓글 내용")
                field("parentId", "댓글 부모 ID", optional = true)
            }
            responseHeaders {
                header(HttpHeaders.LOCATION, "게시글 URI")
            }
        }
    }

    @Test
    fun `댓글 목록 조회`() {
        val response = listOf(
            CommentQuery(
                memberId = 1L,
                memberName = "test1",
                id = 1L,
                content = "유익한 게시글 입니다.",
                mutableListOf(
                    CommentQuery(
                        memberId = 2L,
                        memberName = "test2",
                        id = 2L,
                        content = "유익한 부모 댓글 입니다.",
                        isDeleted = false,
                    )
                ),
                isDeleted = false,
            )
        )

        every { commentQueryService.getComments(any()) } returns response

        mockMvc.GET("/board/articles/{articleId}/comments", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 댓글"
            summary = "댓글 목록 조회"
            responseBody<List<CommentQuery>> {
                field("[].memberId", "작성자 ID")
                field("[].memberName", "작성자 닉네임")
                field("[].id", "댓글 ID")
                field("[].content", "댓글 내용")
                field("[].isDeleted", "댓글 삭제 여부")
                field("[].children[].memberId", "작성자 ID")
                field("[].children[].memberName", "작성자 닉네임")
                field("[].children[].id", "자식 댓글 ID")
                field("[].children[].content", "자식 댓글 내용")
                field("[].children[].isDeleted", "댓글 삭제 여부")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `댓글 수정`() {
        val request = CommentUpdateCommand("너무너무 재밌어요")

        mockMvc.PUT("/board/comments/{id}", 2L) {
            jsonContent(request)
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 댓글"
            summary = "댓글 수정"

            pathVariables {
                param("id", "댓글 ID")
            }
            requestBody<CommentUpdateCommand> {
                field("content", "댓글 내용")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `댓글 삭제`() {
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

