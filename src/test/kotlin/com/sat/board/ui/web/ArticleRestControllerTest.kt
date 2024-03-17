package com.sat.board.ui.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.sat.board.application.ArticleCommandService
import com.sat.board.application.ArticleQueryService
import com.sat.board.application.dto.command.ArticleCreateCommand
import com.sat.board.application.dto.command.ArticleUpdateCommand
import com.sat.board.application.dto.query.ArticleQuery
import com.sat.board.application.dto.query.ArticleSimpleQuery
import com.sat.common.documentation.Documentation
import com.sat.common.documentation.dsl.*
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

@DisplayName(value = "API 문서화 - 게시글")
@WebMvcTest(ArticleRestController::class)
class ArticleRestControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
) : Documentation() {

    @MockkBean
    lateinit var articleCommandService: ArticleCommandService

    @MockkBean
    lateinit var articleQueryService: ArticleQueryService

    @Test
    fun `게시글 생성`() {
        val request = ArticleCreateCommand("테스트 제목", "테스트 내용", 10L)

        every { articleCommandService.create(any()) } returns 1L

        mockMvc.POST("/board/articles") {
            content = objectMapper.writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
        }.andExpect {
            status { isCreated() }
        }.andDocument {
            tag = "게시판 > 게시글"
            summary = "게시글 생성"
            requestBody {
                field("title", "게시글 제목")
                field("content", "게시글 내용")
                field("categoryId", "카테고리 ID")
            }
            responseHeaders {
                header(HttpHeaders.LOCATION, "게시글 URI")
            }
        }
    }

    @Test
    fun `게시글 조회`() {
        every { articleQueryService.get(any()) } returns ArticleQuery(1L, "제목", "내용", "IT")

        mockMvc.GET("/board/articles/{articleId}", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 게시글"
            summary = "게시글 조회"
            pathVariables {
                param("articleId", "게시글 ID")
            }
            responseBody {
                field("id", "게시글 ID")
                field("title", "게시글 제목")
                field("content", "게시글 내용")
                field("category", "카테고리")
            }
        }
    }

    @Test
    fun `게시글 목록 조회`() {
        val response = listOf(
            ArticleSimpleQuery(1L, "제목 A", "IT"),
            ArticleSimpleQuery(2L, "제목 B", "IT"),
            ArticleSimpleQuery(3L, "제목 C", "스포츠"),
        )
        every { articleQueryService.get() } returns response

        mockMvc.GET("/board/articles") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 게시글"
            summary = "게시글 목록 조회"
            responseBody {
                field("[].id", "게시글 ID")
                field("[].title", "게시글 제목")
                field("[].category", "카테고리")
            }
        }
    }

    @Test
    fun `게시글 수정`() {
        val request = ArticleUpdateCommand("수정 제목", "수정 내용", 10L)

        every { articleCommandService.update(any(), any()) } just runs

        mockMvc.PUT("/board/articles/{articleId}", 1L) {
            content = objectMapper.writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 게시글"
            summary = "게시글 수정"
            pathVariables {
                param("articleId", "게시글 ID")
            }
            requestBody {
                field("title", "게시글 제목")
                field("content", "게시글 내용")
                field("categoryId", "카테고리 ID")
            }
        }
    }

    @Test
    fun `게시글 삭제`() {
        every { articleCommandService.delete(any()) } just runs

        mockMvc.DELETE("/board/articles/{articleId}", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 게시글"
            summary = "게시글 삭제"
            description = "Soft delete 수행"
            pathVariables {
                param("articleId", "게시글 ID")
            }
        }
    }
}

