package com.sat.board.ui.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.sat.board.application.command.ArticleCommandService
import com.sat.board.application.command.dto.ArticleCreateCommand
import com.sat.board.application.command.dto.ArticleUpdateCommand
import com.sat.board.application.query.ArticleQueryService
import com.sat.board.application.query.dto.ArticleQuery
import com.sat.board.application.query.dto.ArticleSimpleQuery
import com.sat.board.domain.dto.query.ArticleWithCount
import com.sat.common.documentation.Documentation
import com.sat.common.documentation.dsl.*
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
        val request = ArticleCreateCommand("테스트 제목", "테스트 내용", 1L)

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
                type = request::class
                field("title", "게시글 제목")
                field("content", "게시글 내용")
                field("categoryId", "카테고리 ID")
            }
            responseHeaders {
                header(HttpHeaders.LOCATION, "게시글 URI")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `게시글 조회`() {
        every { articleQueryService.get(any(), any()) } returns
                ArticleQuery(1L, "제목", "내용", "IT", false, 1L)

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
                type = ArticleQuery::class
                field("id", "게시글 ID")
                field("title", "게시글 제목")
                field("content", "게시글 내용")
                field("category", "카테고리")
                field("hasLike", "좋아요 여부")
                field("createdBy", "게시글 작성자 ID")
            }
        }
    }

    @Test
    fun `게시글 목록 조회`() {
        val response = listOf(
            ArticleWithCount(1L, "제목 A", "IT", 0 , 0),
            ArticleWithCount(2L, "제목 B", "IT", 0, 0),
            ArticleWithCount(3L, "제목 C", "스포츠", 0, 0),
        )
        every { articleQueryService.getAll(any()) } returns response

        mockMvc.GET("/board/articles") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 게시글"
            summary = "게시글 목록 조회"
            responseBody {
                type = ArticleSimpleQuery::class
                field("[].id", "게시글 ID")
                field("[].title", "게시글 제목")
                field("[].category", "카테고리")
                field("[].commentCount", "댓글 수")
                field("[].likeCount", "좋아요 수")
            }
        }
    }

    @Test
    fun `게시글 수정`() {
        val request = ArticleUpdateCommand("수정 제목", "수정 내용")

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
                type = request::class
                field("title", "게시글 제목")
                field("content", "게시글 내용")
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

    @WithAuthenticatedUser
    @Test
    fun `게시글 좋아요`() {
        every { articleCommandService.like(any(), any()) } just runs

        mockMvc.POST("/board/articles/{articleId}:like", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 게시글"
            summary = "게시글 좋아요"
            description = "게시글에 좋아요를 표시"
            pathVariables {
                param("articleId", "게시글 ID")
            }
        }
    }
}

