package com.sat.board.application

import com.ninjasquad.springmockk.MockkBean
import com.sat.SpringBootTestWithProfile
import com.sat.board.application.command.CommentCommandService
import com.sat.board.application.command.dto.CommentCreateCommand
import com.sat.board.application.command.dto.CommentUpdateCommand
import com.sat.board.application.query.CommentQueryService
import com.sat.board.domain.Article
import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import com.sat.board.domain.Comment
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CategoryRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.common.security.WithAuthenticatedUser
import com.sat.user.domain.Member
import com.sat.user.domain.port.repository.MemberRepository
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

@DisplayName(value = "서비스 - 댓글 기능 테스트")
@SpringBootTestWithProfile
class CommentCommandServiceTest @Autowired constructor(
    private val commentRepository: CommentRepository,
    private val commentCommandService: CommentCommandService,
    private val commentQueryService: CommentQueryService,
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
) {

    @MockkBean
    lateinit var memberRepository: MemberRepository

    lateinit var 게시글: Article

    @BeforeEach
    fun setData() {
        val category = Category(CategoryName("IT"))
        categoryRepository.save(category)

        게시글 = Article("클린코드", "너무 유익한 책입니다.", category, 1L)
        articleRepository.save(게시글)
    }

    @AfterEach
    fun clean() {
        commentRepository.deleteAll()
    }

    @WithAuthenticatedUser
    @Disabled
    @Test
    fun `댓글 생성 테스트`() {
        val request = CommentCreateCommand("너무 재밌어요")
        val 게시글_ID = 게시글.id!!
        commentCommandService.create(게시글_ID, request)

        val comments = commentQueryService.get(게시글_ID)
        assertThat(comments).hasSize(1)
    }

    @WithAuthenticatedUser
    @Test
    fun `댓글 생성시 게시글이 존재하지 않으면 예외가 발생한다`() {
        val request = CommentCreateCommand("너무 재밌어요")

        val invalidArticleId = 10L
        val message = assertThrows<IllegalStateException> {
            commentCommandService.create(invalidArticleId, request)
        }.message
        println(message)
    }

    @WithAuthenticatedUser
    @Disabled
    @Test
    fun `자식 댓글 생성 테스트`() {
        val 게시글_ID = 게시글.id!!
        val newComment = Comment(게시글_ID, "너무 재밌어요")
        commentRepository.save(newComment)
        val request = CommentCreateCommand("보고싶어요", newComment.id)
        commentCommandService.create(게시글_ID, request)

        every { memberRepository.findAllById(any()) } returns listOf(Member("김영철", "김영철", "", 1L))

        val comments = commentQueryService.get(게시글_ID)
        assertThat(comments).hasSize(1)
        assertThat(comments[0].children).hasSize(1)
        assertThat(comments[0].children[0].parentId).isEqualTo(comments[0].id)
    }

    @WithAuthenticatedUser
    @Test
    fun `존재하지 않는 부모아이디로 자식 댓글을 생성하면 예외가 발생한다`() {
        val 게시글_ID = 게시글.id!!
        val request = CommentCreateCommand("보고싶어요", 10L)

        val message = assertThrows<IllegalArgumentException> {
            commentCommandService.create(게시글_ID, request)
        }.message
        println(message)
    }

    @WithAuthenticatedUser
    @Test
    fun `댓글 수정 테스트`() {
        val 게시글_ID = 게시글.id!!
        val newComment = Comment(게시글_ID, "너무 재밌어요")
        commentRepository.save(newComment)

        val comment = commentRepository.findByIdOrNull(newComment.id)!!

        val updateRequest = CommentUpdateCommand("너무 재미없어요")
        commentCommandService.update(comment.id!!, updateRequest, 1L)

        val findComment = commentRepository.findByIdOrNull(comment.id)!!
        assertThat(findComment.content).isEqualTo("너무 재미없어요")
    }

    @WithAuthenticatedUser
    @Test
    fun `삭제된 댓글을 수정하면 에러가 발생한다`() {
        val 게시글_ID = 게시글.id!!
        val newComment = Comment(게시글_ID, "너무 재밌어요")
        commentRepository.save(newComment)

        val comment = commentRepository.findByIdOrNull(newComment.id)!!
        commentCommandService.delete(newComment.id!!, 1L)

        val updateRequest = CommentUpdateCommand("너무 재미없어요")
        val message = assertThrows<IllegalStateException> {
            commentCommandService.update(comment.id!!, updateRequest, 1L)
        }.message
        println(message)
    }

    @WithAuthenticatedUser
    @Test
    fun `다른 사용자의 댓글을 수정하면 예외가 발생한다`() {
        val 게시글_ID = 게시글.id!!
        val newComment = Comment(게시글_ID, "너무 재밌어요")
        commentRepository.save(newComment)

        val comment = commentRepository.findByIdOrNull(newComment.id)!!
        val updateRequest = CommentUpdateCommand("너무 재미없어요")

        val message = assertThrows<IllegalStateException> {
            commentCommandService.update(comment.id!!, updateRequest, 2L)
        }.message
        println(message)
    }

    @WithAuthenticatedUser
    @Test
    fun `댓글 삭제 테스트`() {
        val 게시글_ID = 게시글.id!!
        val newComment = Comment(게시글_ID, "너무 재밌어요")
        commentRepository.save(newComment)

        commentCommandService.delete(newComment.id!!, 1L)
        val findComment = commentRepository.findByIdOrNull(newComment.id!!)!!
        assertThat(findComment.isDeleted).isTrue()
    }
}
