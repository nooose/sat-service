package com.sat.user.application

import com.ninjasquad.springmockk.MockkBean
import com.sat.KEY
import com.sat.board.domain.Article
import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import com.sat.board.domain.port.ArticleRepository
import com.sat.common.utils.findByIdOrThrow
import com.sat.user.domain.Member
import com.sat.user.domain.Point
import com.sat.user.domain.PointType
import com.sat.user.domain.port.LoginHistoryRepository
import com.sat.user.domain.port.PointRepository
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

@DisplayName("Point 테스트")
@SpringBootTest(properties = ["jwt.secret-key=$KEY"])
class PointServiceTest @Autowired constructor(
    private val pointService: PointService,
    private val pointRepository: PointRepository,
    @MockkBean
    private val articleRepository: ArticleRepository,
    @MockkBean
    private val loginHistoryRepository: LoginHistoryRepository,
    @MockkBean
    private val memberLoginService: MemberLoginService,
) : BehaviorSpec({

    afterEach {
        pointRepository.deleteAll()
        println("저장소 초기화")
    }

    Given("로그인을 하고") {
        val loginMember = Member("김영철", "김영철", "aaa@google.com", 1L)
        val now = LocalDateTime.now()

        every { memberLoginService.createLoginHistory(any(), any()) } just runs
        every { loginHistoryRepository.existsByLoginDateTimeAfter(any()) } returns false

        When("최초 로그인이면") {
            pointService.dailyPointAward(loginMember.id!!, now)

            Then("포인트가 적립된다.") {
                val points = pointRepository.findAllByMemberId(loginMember.id!!)
                assertThat(points.total()).isEqualTo(PointType.LOGIN.score)
            }
        }

        When("하루에 로그인을 두번 이상하면") {
            every { loginHistoryRepository.existsByLoginDateTimeAfter(any()) } returns true

            pointService.dailyPointAward(loginMember.id!!, now)

            Then("포인트가 중복 적립되지 않는다") {
                val points = pointRepository.findAllByMemberId(loginMember.id!!)
                assertThat(points.total()).isEqualTo(0)
            }
        }
    }

    Given("로그인한 사용자가 있고") {
        val loginMember = Member("김영철", "김영철", "aaa@google.com", 1L)

        When("게시글을 작성하면") {
            pointService.articlePointAward(loginMember.id!!)
        }

        Then("포인트가 적립된다") {
            val memberPoint = pointRepository.findAllByMemberId(loginMember.id!!)
            assertThat(memberPoint.sumOf { it.point }).isEqualTo(PointType.ARTICLE.score)
        }
    }

    Given("댓글을 작성하고") {
        val me = Member("김영철", "김영철", "test@google.com", 1L)
        val category = Category(CategoryName("김영철의집"))
        val otherArticle = Article("다른 사람의 게시글", "서울에서 왔습니다", category, 1L)
        ReflectionTestUtils.setField(otherArticle, "createdBy", 100L)
        val myArticle = Article("내 게시글", "서울에서 왔습니다", category, 10L)
        ReflectionTestUtils.setField(myArticle, "createdBy", me.id)

        every { articleRepository.findByIdOrThrow(otherArticle.id!!) { "" } } returns otherArticle

        When("내가 작성한 게시글이 아니면") {
            pointService.commentPointAward(otherArticle.id!!, me.id!!)

            Then("포인트가 쌓인다") {
                val points = pointRepository.findAllByMemberId(me.id!!)
                assertThat(points.total()).isEqualTo(PointType.COMMENT.score)
            }
        }
        When("내가 작성한 게시글이면") {
            every { articleRepository.findByIdOrThrow(myArticle.id!!) { "" } } returns myArticle

            pointService.commentPointAward(myArticle.id!!, me.id!!)

            Then("포인트가 쌓이지 않는다") {
                val points = pointRepository.findAllByMemberId(me.id!!)
                assertThat(points.total()).isEqualTo(0)
            }
        }
    }
})

private fun List<Point>.total(): Int {
    return this.sumOf { it.point }
}
