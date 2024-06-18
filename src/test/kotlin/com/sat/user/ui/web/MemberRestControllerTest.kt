package com.sat.user.ui.web

import com.ninjasquad.springmockk.MockkBean
import com.sat.board.query.*
import com.sat.common.documentation.Documentation
import com.sat.common.documentation.dsl.GET
import com.sat.common.documentation.dsl.PUT
import com.sat.common.documentation.dsl.andDocument
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.common.security.WithAuthenticatedUser
import com.sat.user.command.application.MemberCommandService
import com.sat.user.command.application.MemberUpdateCommand
import com.sat.user.command.domain.point.PointType
import com.sat.user.query.MemberQueryService
import com.sat.user.query.PointQueryService
import com.sat.user.query.MemberInformation
import com.sat.user.query.MemberSimpleQuery
import com.sat.user.query.MyPointQuery
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import java.time.LocalDateTime

@DisplayName(value = "API 문서화 - 사용자")
@WebMvcTest(MemberRestController::class)
class MemberRestControllerTest : Documentation() {

    @MockkBean
    lateinit var memberCommandService: MemberCommandService
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
            responseBody<MemberInformation> {
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
            responseBody<List<MemberSimpleQuery>> {
                field("[].id", "사용자 ID")
                field("[].name", "이름")
                field("[].email", "이메일")
                field("[].createdDateTime", "가입 시간")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `자신의 정보 수정`() {
        val request = MemberUpdateCommand("닉네임")

        every { memberCommandService.update(any(), any()) } just runs

        mockMvc.PUT("/user/members/me") {
            jsonContent(request)
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "사용자"
            summary = "사용자 정보 수정"
            requestBody<MemberUpdateCommand> {
                field("nickname", "변경할 닉네임")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `자신이 작성한 게시글 목록 조회`() {
        val responses = listOf(
            ArticleWithCountQuery(1, "테스트 A", "IT", 10, 15, LocalDateTime.now()),
            ArticleWithCountQuery(2, "테스트 B", "IT", 3, 5, LocalDateTime.now())
        )

        every { articleQueryService.getAll(any()) } returns responses

        mockMvc.GET("/user/articles") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "사용자"
            summary = "자신이 작성한 게시글 목록 조회"
            responseBody<List<ArticleWithCountQuery>> {
                field("[].id", "게시글 ID")
                field("[].title", "게시글 제목")
                field("[].category", "게시글 카테고리")
                field("[].commentCount", "게시글에 달린 댓글 수")
                field("[].likeCount", "게시글에 달린 좋아요 수")
                field("[].createdDateTime", "게시글 작성 시간")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `자신이 작성한 댓글 목록 조회`() {
        val responses = listOf(
            CommentWithArticleQuery(1, "테스트 A", 1L, "제목 A", LocalDateTime.now()),
            CommentWithArticleQuery(2, "테스트 B", 2L, "제목 B", LocalDateTime.now()),
        )

        every { commentQueryService.getComments(any(), any()) } returns PageCursor(CursorRequest(2), responses)

        mockMvc.GET("/user/comments") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "사용자"
            summary = "자신이 작성한 댓글 목록 조회"
            queryParams {
                param("id", "커서 ID", optional = true)
                param("size", "페이지 사이즈", optional = true)
            }
            responseBody<PageCursor<CommentWithArticleQuery>> {
                field("nextCursor.id", "다음 커서 ID")
                field("nextCursor.size", "다음 페이지 사이즈")
                field("data[].id", "댓글 ID")
                field("data[].content", "댓글 내용")
                field("data[].articleId", "게시글 ID")
                field("data[].articleTitle", "게시글 제목")
                field("data[].createdDateTime", "댓글 작성 시간")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `포인트 이력 조회`() {
        val responses = listOf(
            MyPointQuery(1, 10, PointType.LOGIN, LocalDateTime.now()),
            MyPointQuery(2, 10, PointType.LOGIN, LocalDateTime.now().plusDays(1)),
        )

        every { pointQueryService.getPoints(any(), any()) } returns PageCursor(CursorRequest(2), responses)

        mockMvc.GET("/user/points") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "사용자"
            summary = "포인트 적립/사용 이력 조회"
            queryParams {
                param("id", "커서 ID", optional = true)
                param("size", "페이지 사이즈", optional = true)
            }
            responseBody<PageCursor<MyPointQuery>> {
                field("nextCursor.id", "다음 커서 ID")
                field("nextCursor.size", "다음 페이지 사이즈")
                field("data[].id", "포인트 ID")
                field("data[].point", "포인트 적립/사용 값")
                field("data[].type", "포인트 적립/사용 타입")
                field("data[].createdDateTime", "포인트 적립/사용 시간")
            }
        }
    }

    @WithAuthenticatedUser
    @Test
    fun `좋아요 누른 게시글 목록 조회`() {
        val responses = listOf(
            LikedArticleSimpleQuery(1, 1, "좋아요 A", LocalDateTime.now()),
            LikedArticleSimpleQuery(1, 2, "좋아요 B", LocalDateTime.now()),
        )

        every { articleQueryService.getLikedArticles(any(), any()) } returns PageCursor(CursorRequest(2), responses)

        mockMvc.GET("/user/likes") {
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "사용자"
            summary = "좋아요 누른 게시글 목록 조회"
            responseBody<PageCursor<LikedArticleSimpleQuery>> {
                field("nextCursor.id", "다음 커서 ID")
                field("nextCursor.size", "다음 페이지 사이즈")
                field("data[].id", "좋아요 ID")
                field("data[].articleId", "게시글 ID")
                field("data[].articleTitle", "게시글 제목")
                field("data[].createdDateTime", "게시글 작성일")
            }
        }
    }
}
