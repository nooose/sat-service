package com.sat.user.application

import com.sat.KEY
import com.sat.board.domain.Article
import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import com.sat.board.domain.Comment
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CategoryRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.user.domain.Member
import com.sat.user.domain.Point
import com.sat.user.domain.PointType
import com.sat.user.domain.port.PointRepository
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import org.assertj.core.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

@DisplayName("Point 테스트")
@SpringBootTest(properties = ["jwt.secret-key=$KEY"])
class PointServiceTest @Autowired constructor(
    private val pointService: PointService,
    private val memberLoginService: MemberLoginService,
    private val pointRepository: PointRepository,
    private val commentRepository: CommentRepository,
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
) : BehaviorSpec({

    afterEach {
        pointRepository.deleteAll()
        println("저장소 초기화")
    }

    Given("로그인을 하고") {
        val loginMember = Member("김영철", "김영철", "aaa@google.com", 1L)
        val now = LocalDateTime.now()
        val oneHourLater = now.plusHours(1)
        val nextDay = now.plusDays(1)

        When("최초 로그인이면") {
            pointService.dailyPointAward(loginMember.id!!, now.toLocalDate(), PointType.LOGIN)
            memberLoginService.createLoginHistory(loginMember.id!!, now)

            Then("포인트가 적립된다.") {
                val points = pointRepository.findAllByMemberId(loginMember.id!!)
                assertThat(points.total()).isEqualTo(PointType.LOGIN.score)
            }
        }

        When("한시간 뒤 로그인을 하면") {
            pointService.dailyPointAward(loginMember.id!!, oneHourLater.toLocalDate(), PointType.LOGIN)
            memberLoginService.createLoginHistory(loginMember.id!!, now)

            Then("포인트가 적립되지 않는다") {
                val points = pointRepository.findAllByMemberId(loginMember.id!!)
                assertThat(points.total()).isEqualTo(0)
            }
        }

        When("다음날 로그인을 하면") {
            pointService.dailyPointAward(loginMember.id!!, nextDay.toLocalDate(), PointType.LOGIN)
            memberLoginService.createLoginHistory(loginMember.id!!, now)

            Then("포인트가 적립된다") {
                val points = pointRepository.findAllByMemberId(loginMember.id!!)
                assertThat(points.sumOf { it.point }).isEqualTo(PointType.LOGIN.score * points.size)
            }
        }
    }

    Given("로그인한 사용자가 있고") {
        val loginMember = Member("김영철", "김영철", "aaa@google.com", 1L)
        When("게시글을 작성하면") {
            pointService.articlePointAward(loginMember.id!!, PointType.ARTICLE)
        }
        Then("포인트가 적립된다") {
            val memberPoint = pointRepository.findAllByMemberId(loginMember.id!!)
            assertThat(memberPoint.sumOf { it.point }).isEqualTo(PointType.ARTICLE.score)
        }
    }

    Given("댓글을 작성하고") {
        val 댓글_작성자 = Member("김영철", "김영철", "test@google.com", 2L)

        val category = categoryRepository.save(Category(CategoryName("김영철의집")))
        val article = Article("김영철입니다", "서울에서 왔습니다", category)
        articleRepository.save(article)
        ReflectionTestUtils.setField(article, "createdBy", 10L)

        val comment = Comment(article.id!!, "김영철이누구야")
        commentRepository.save(comment)
        ReflectionTestUtils.setField(comment, "createdBy", 댓글_작성자.id)

        val myArticle = Article("김영철입니다", "서울에서 왔습니다", category)
        articleRepository.save(myArticle)
        ReflectionTestUtils.setField(myArticle, "createdBy", 댓글_작성자.id)

        When("내가 작성한 게시글이 아니면") {
            pointService.commentPointAward(article.id!!, comment.createdBy!!, PointType.COMMENT)

            Then("포인트가 쌓인다") {
                val points = pointRepository.findAllByMemberId(댓글_작성자.id!!)
                assertThat(points.total()).isEqualTo(PointType.COMMENT.score)
            }
        }
        When("내가 작성한 게시글이면") {
            pointService.commentPointAward(myArticle.id!!, comment.createdBy!!, PointType.COMMENT)

            Then("포인트가 쌓이지 않는다") {
                val points = pointRepository.findAllByMemberId(댓글_작성자.id!!)
                assertThat(points.total()).isEqualTo(PointType.COMMENT.score)
            }
        }
    }
})

private fun List<Point>.total(): Int {
    return this.sumOf { it.point }
}
