package com.sat.user.application

import com.sat.IntegrationTest
import com.sat.board.application.command.ArticleCommandService
import com.sat.board.application.command.dto.ArticleCreateCommand
import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CategoryRepository
import com.sat.common.domain.CursorRequest
import com.sat.common.security.TestAuthUtils.setAuthentication
import com.sat.user.application.command.MemberLoginService
import com.sat.user.application.command.PointCommandService
import com.sat.user.application.query.PointQueryService
import com.sat.user.domain.PointType
import com.sat.user.domain.port.repository.LoginHistoryRepository
import com.sat.user.domain.port.repository.PointRepository
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.*
import java.time.LocalDateTime

@DisplayName("서비스 - Point 테스트")
@IntegrationTest
class PointServiceIntegrationTest (
    private val pointCommandService: PointCommandService,
    private val pointQueryService: PointQueryService,
    private val memberLoginService: MemberLoginService,
    private val categoryRepository: CategoryRepository,
    private val articleRepository: ArticleRepository,
    private val articleCommandService: ArticleCommandService,
    private val pointRepository: PointRepository,
    private val memberLoginHistoryRepository: LoginHistoryRepository,
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    Given("로그인을 하고") {
        val now = LocalDateTime.now()
        val loginMember = memberLoginService.login("김영철", "aaa@google.com")

        When("최초 로그인이면") {
            pointCommandService.dailyPointAward(loginMember.id, now)

            Then("포인트가 적립된다.") {
                val point = pointQueryService.getTotalPoint(loginMember.id)
                point shouldBe PointType.LOGIN.score
            }
        }

        When("하루에 로그인을 두번 이상해도") {
            val loginTime = LocalDateTime.of(2024, 1, 1, 12, 0)
            pointCommandService.dailyPointAward(loginMember.id, loginTime)
            pointCommandService.dailyPointAward(loginMember.id, loginTime.plusHours(5))
            pointCommandService.dailyPointAward(loginMember.id, loginTime.plusHours(10))

            Then("포인트가 중복 적립되지 않는다") {
                val point = pointQueryService.getTotalPoint(loginMember.id)
                point shouldBe PointType.LOGIN.score
            }
        }
    }

    Given("로그인한 사용자가 있고") {
        val loginMember = memberLoginService.login("김영철", "aaa@google.com")

        When("게시글을 작성하면") {
            pointCommandService.articlePointAward(loginMember.id)

            Then("포인트가 적립된다") {
                val memberPoint = pointQueryService.getTotalPoint(loginMember.id)
                memberPoint shouldBe PointType.ARTICLE.score
            }
        }
    }

    Given("게시글과 댓글을 작성하고") {
        val category = categoryRepository.save(Category(CategoryName("컴퓨터")))
        setAuthentication(1L)
        val myArticleId = articleCommandService.create(ArticleCreateCommand("내 게시글", "내용 없음", category.id))
        setAuthentication(5L)
        val otherArticleId = articleCommandService.create(ArticleCreateCommand("다른 게시글", "내용 없음", category.id))
        When("내가 작성한 게시글이 아니면") {
            pointCommandService.commentPointAward(otherArticleId, 1L)

            Then("포인트가 쌓인다") {
                val point = pointQueryService.getTotalPoint(1L)
                point shouldBe PointType.ARTICLE.score + PointType.COMMENT.score
            }
        }

        When("내가 작성한 게시글이면") {
            pointCommandService.commentPointAward(myArticleId, 1L)

            Then("포인트가 쌓이지 않는다") {
                val point = pointQueryService.getTotalPoint(1L)
                point shouldBe PointType.ARTICLE.score
            }
        }
    }

    Given("포인트를 쌓고") {
        val loginMember = memberLoginService.login("김영철", "aaa@google.com")
        val now = LocalDateTime.now()
        pointCommandService.dailyPointAward(loginMember.id, now)
        pointCommandService.dailyPointAward(loginMember.id, now.plusDays(7))
        When("포인트를 조회하면") {
            val totalPoint = pointQueryService.getTotalPoint(loginMember.id)
            val cursorResponse = pointQueryService.getPoints(loginMember.id, CursorRequest.default())
            Then("포인트 정보를 확인할 수 있다.") {
                assertSoftly {
                    totalPoint shouldBe 20
                    cursorResponse.data shouldHaveSize 2
                }
            }
        }
    }

    afterEach {
        articleRepository.deleteAll()
        pointRepository.deleteAll()
        categoryRepository.deleteAll()
        memberLoginHistoryRepository.deleteAll()
    }
})
