package com.sat.board.ui.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.sat.board.application.CategoryCommandService
import com.sat.board.application.CategoryQueryService
import com.sat.board.application.dto.command.CategoryCreateCommand
import com.sat.board.application.dto.command.CategoryUpdateCommand
import com.sat.board.application.dto.query.CategoryQuery
import com.sat.common.documentation.Documentation
import com.sat.common.documentation.dsl.*
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType


@DisplayName(value = "API 문서화 - 카테고리")
@WebMvcTest(CategoryRestController::class)
class CategoryRestControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
) : Documentation() {

    @MockkBean
    lateinit var categoryCommandService : CategoryCommandService

    @MockkBean
    lateinit var categoryQueryService: CategoryQueryService

    @Test
    fun `카테고리 생성`() {
        val request = CategoryCreateCommand(name = "IT", 1L)

        every { categoryCommandService.create(any()) } just runs

        mockMvc.POST("/board/categories") {
            content = objectMapper.writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 카테고리"
            summary = "카테고리 생성"
            requestBody {
                type = request::class
                field("name", "카테고리 이름")
                field("parentId", "카테고리 부모 ID", optional = true)
            }
        }
    }

    @Test
    fun `카테고리 조회`() {
        val response = listOf(CategoryQuery(1L, "컴퓨터", mutableListOf(CategoryQuery(2L, "맥북", parentId = 1L))))

        every { categoryQueryService.get() } returns response

        mockMvc.GET("/board/categories") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 카테고리"
            summary = "카테고리 조회"
            responseBody {
                type = response::class
                field("[].id", "카테고리 ID")
                field("[].name", "카테고리 이름")
                field("[].children[].id", "자식 카테고리 ID")
                field("[].children[].name", "자식 카테고리 이름")
                field("[].children[].parentId", "부모 카테고리 ID")
            }
        }
    }

    @Test
    fun `카테고리 수정`() {
        val request = CategoryUpdateCommand("수정", 1L)

        every { categoryCommandService.update(any(), any()) } just runs

        mockMvc.PUT("/board/categories/{id}", 2L) {
            content = objectMapper.writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
        }.andExpect {
            status { isOk()}
        }.andDocument {
            tag = "게시판 > 카테고리"
            summary = "카테고리 수정"
            pathVariables {
                param("id", "카테고리 ID")
            }
            requestBody {
                type = request::class
                field("name" , "카테고리 이름")
                field("parentId" , "카테고리 부모 ID", optional = true)
            }
        }
    }

    @Test
    fun `카테고리 삭제`() {
        every { categoryCommandService.delete(any()) } just runs

        mockMvc.DELETE("/board/categories/{id}", 1L) {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 카테고리"
            summary = "카테고리 삭제"
            description = "Hard delete 수행"
            pathVariables {
                param("id", "카테고리 ID" )
            }
        }
    }
}
