package com.sat.board.application

import com.sat.IntegrationTest
import com.sat.board.command.application.CommentCommandService
import com.sat.board.command.application.CommentCreateCommand
import com.sat.board.command.application.CommentUpdateCommand
import com.sat.board.query.CommentQueryService
import com.sat.board.command.domain.article.Article
import com.sat.board.command.domain.article.Category
import com.sat.board.command.domain.article.CategoryName
import com.sat.board.command.domain.comment.Comment
import com.sat.board.command.domain.article.ArticleRepository
import com.sat.board.command.domain.article.CategoryRepository
import com.sat.board.command.domain.comment.CommentRepository
import com.sat.common.security.TestAuthUtils.setAuthentication
import com.sat.user.command.application.MemberLoginService
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.springframework.data.repository.findByIdOrNull

@DisplayName("서비스 - 댓글 기능 테스트")
@IntegrationTest
class CommentCommandServiceTest(
    private val commentRepository: CommentRepository,
    private val commentCommandService: CommentCommandService,
    private val commentQueryService: CommentQueryService,
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
    private val memberLoginService: MemberLoginService,
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val loginMember = memberLoginService.login("김영철", "test@test.com")

    beforeEach {
        setAuthentication(loginMember.id)
    }

    Given("게시글이 있고") {
        println("게시글이 있고")
        val category = categoryRepository.save(Category(CategoryName("IT")))
        val article = articleRepository.save(Article("클린코드", "너무 유익해요", category))

        When("댓글을 작성하면") {
            commentCommandService.create(article.id, CommentCreateCommand("댓글 A"))
            Then("조회할 수 있다.") {
                val comments = commentRepository.findAll()
                assertSoftly {
                    comments shouldHaveSize 1
                    comments.first().content shouldBe "댓글 A"
                }
            }
        }

        When("존재하지 않는 댓글에 대댓글을 작성하면") {
            val request = CommentCreateCommand("보고싶어요", Long.MAX_VALUE)
            Then("예외를 던진다.") {
                shouldThrow<IllegalStateException> {
                    commentCommandService.create(article.id, request)
                }.apply { println(this.message) }
            }
        }
    }

    Given("게시글이 없는데") {
        When("댓글을 작성하면") {
            Then("댓글 작성이 실패한다.") {
                shouldThrow<IllegalStateException> {
                    commentCommandService.create(Long.MAX_VALUE, CommentCreateCommand("댓글 A"))
                }.apply { println(this.message) }
            }
        }
    }

    Given("게시글에 댓글이 있고") {
        val category = categoryRepository.save(Category(CategoryName("IT")))
        val article = articleRepository.save(Article("클린코드", "너무 유익해요", category))
        val commentA = commentRepository.save(Comment(article.id, "댓글 A"))
        When("대댓글을 작성하면") {
            commentCommandService.create(article.id, CommentCreateCommand("댓글 B", commentA.id))
            Then("조회할 수 있다.") {
                val comments = commentQueryService.get(article.id)
                comments shouldHaveSize 1

                assertSoftly {
                    comments shouldHaveSize 1
                    comments[0].content shouldBe "댓글 A"
                    comments[0].children shouldHaveSize 1
                    comments[0].children[0].content shouldBe "댓글 B"
                }
            }
        }

        When("댓글을 수정하면") {
            val command = CommentUpdateCommand("댓글 수정됨")
            commentCommandService.update(commentA.id, command, loginMember.id)
            Then("댓글이 수정된다.") {
                val comment = commentRepository.findByIdOrNull(commentA.id)!!
                comment.content shouldBe "댓글 수정됨"
            }
        }

        When("댓글을 삭제하면") {
            commentCommandService.delete(commentA.id, loginMember.id)
            Then("삭제처리 된다.") {
                val comment = commentRepository.findByIdOrNull(commentA.id)!!
                comment.isDeleted.shouldBeTrue()
            }
        }

        When("댓글 주인이 아닌 제3자가 삭제를 수정하면") {
            Then("삭제되지 않는다.") {
                shouldThrow<IllegalStateException> {
                    commentCommandService.delete(commentA.id, loginMember.id + 1L)
                }.apply { println(this.message) }
            }
        }
    }

    Given("삭제처리된 댓글에") {
        val category = categoryRepository.save(Category(CategoryName("IT")))
        val article = articleRepository.save(Article("클린코드", "너무 유익해요", category))
        val commentA = commentRepository.save(Comment(article.id, "댓글 A"))

        commentCommandService.delete(commentA.id, loginMember.id)

        When("수정 요청을 하면") {
            val command = CommentUpdateCommand("댓글 수정됨")
            Then("수정되지 않는다.") {
                shouldThrow<IllegalStateException> {
                    val request = CommentUpdateCommand("수정요청")
                    commentCommandService.update(commentA.id, request, loginMember.id)
                }.apply { println(this.message) }
            }
        }
    }

    afterEach {
        articleRepository.deleteAll()
        categoryRepository.deleteAll()
        commentRepository.deleteAll()
    }
})
