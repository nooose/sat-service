package com.sat.board.ui.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.sat.board.application.ArticleCommandService
import com.sat.board.application.dto.command.ArticleCreateCommand
import com.sat.board.common.documentation.Documentation
import com.sat.board.common.documentation.dsl.POST
import com.sat.board.common.documentation.dsl.andDocument
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType

@DisplayName(value = "API 문서화 - 게시글")
@WebMvcTest(ArticleRestController::class)
class ArticleRestControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
) : Documentation() {

    @MockkBean
    lateinit var articleCommandService: ArticleCommandService

    @Test
    fun `게시글 생성`() {
        val request = ArticleCreateCommand("테스트 제목", "테스트 내용", 10)

        every { articleCommandService.create(any()) } just runs

        mockMvc.POST("/board/articles") {
            content = objectMapper.writeValueAsString(request)
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "게시판 > 게시글"
            summary = "게시글 생성"
            requestBody {
                field("title", "게시글 제목")
                field("content", "게시글 내용")
                field("categoryId", "카테고리 ID")
            }
        }
    }
}

