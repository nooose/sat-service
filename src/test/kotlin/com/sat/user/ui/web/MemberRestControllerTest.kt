package com.sat.user.ui.web

import com.ninjasquad.springmockk.MockkBean
import com.sat.board.application.query.ArticleQueryService
import com.sat.board.application.query.CommentQueryService
import com.sat.common.documentation.Documentation
import com.sat.common.documentation.dsl.GET
import com.sat.common.documentation.dsl.andDocument
import com.sat.common.security.WithAuthenticatedUser
import com.sat.user.application.query.MemberQueryService
import com.sat.user.application.query.PointQueryService
import com.sat.user.application.query.dto.MemberInformation
import com.sat.user.application.query.dto.MemberSimpleQuery
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import java.time.LocalDateTime

@DisplayName(value = "API 문서화 - 사용자")
@WebMvcTest(MemberRestController::class)
class MemberRestControllerTest : Documentation() {

    @MockkBean
    lateinit var memberQueryService: MemberQueryService
    @MockkBean
    lateinit var pointQueryService: PointQueryService
    @MockkBean
    lateinit var articleQueryService: ArticleQueryService
    @MockkBean
    lateinit var commentQueryService: CommentQueryService

    @WithAuthenticatedUser
    @Test
    fun `자신의 정보 조회`() {
        every { pointQueryService.getTotalPoint(any()) } returns 100

        mockMvc.GET("/user/members/me") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "사용자"
            summary = "사용자 목록 조회"
            responseBody {
                type = MemberInformation::class
                field("id", "사용자 ID")
                field("name", "이름")
                field("nickname", "닉네임")
                field("email", "이메일")
                field("avatar", "Avatar 사진 URL")
                field("point", "포인트")
            }
        }
    }

    @Test
    fun `사용자 목록 조회`() {
        val dateTime = LocalDateTime.of(2024, 1, 1, 7, 0)
        val response = listOf(
            MemberSimpleQuery(1L, "홍길동", "adminA@sat.com", dateTime),
            MemberSimpleQuery(2L, "홍동길", "adminB@sat.com", dateTime),
            MemberSimpleQuery(3L, "홍길돔", "adminC@sat.com", dateTime),
        )

        every { memberQueryService.get() } returns response

        mockMvc.GET("/user/members") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "사용자"
            summary = "사용자 목록 조회"
            responseBody {
                type = MemberSimpleQuery::class
                field("[].id", "사용자 ID")
                field("[].name", "이름")
                field("[].email", "이메일")
                field("[].createdDateTime", "가입 시간")
            }
        }
    }
}
