package com.sat.board.application

import com.sat.board.application.dto.command.CommentCreateCommand
import com.sat.board.application.dto.command.CommentUpdateCommand
import com.sat.board.domain.Article
import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import com.sat.board.domain.Comment
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CategoryRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.common.security.WithAuthenticatedUser
import com.sat.user.domain.Member
import com.sat.user.domain.port.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class CommentCommandServiceTest @Autowired constructor(
    private val commentRepository: CommentRepository,
    private val commentCommandService: CommentCommandService,
    private val commentQueryService: CommentQueryService,
    private val articleRepository: ArticleRepository,
    private val categoryRepository: CategoryRepository,
    private val memberRepository: MemberRepository,
) {

    lateinit var 게시글: Article

    @BeforeEach
    fun setData() {
        val member = Member("홍길동", "홍길동", "admin@sat.com", 1L)
        memberRepository.save(member)
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
    fun `게시글이 존재하지 않으면 댓글 생성이 실패한다`() {
        val request = CommentCreateCommand("너무 재밌어요")

        assertThrows<IllegalStateException> { commentCommandService.create(10L, request) }
    }

    @WithAuthenticatedUser
    @Test
    fun `자식 댓글 생성 테스트`() {
        val 게시글_ID = 게시글.id!!
        val newComment = Comment(게시글_ID, "너무 재밌어요")
        commentRepository.save(newComment)

        val request = CommentCreateCommand("보고싶어요", newComment.id)
        commentCommandService.create(게시글_ID, request)

        val comments = commentQueryService.get(게시글_ID)
        assertThat(comments).hasSize(1)
        assertThat(comments[0].children).hasSize(1)
        assertThat(comments[0].children[0].parentId).isEqualTo(comments[0].id)
    }

    @WithAuthenticatedUser
    @Test
    fun `존재하지 않는 부모아이디로 자식 댓글을 생성하면 에러가 발생한다`() {
        val 게시글_ID = 게시글.id!!
        val request = CommentCreateCommand("보고싶어요", 10L)

        assertThrows<IllegalArgumentException> { commentCommandService.create(게시글_ID, request) }
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
        assertThrows<IllegalStateException> { commentCommandService.update(comment.id!!, updateRequest, 1L) }
    }

    @WithAuthenticatedUser
    @Test
    fun `다른 사람의 댓글 수정하면 에러가 발생한다`() {
        val 게시글_ID = 게시글.id!!
        val newComment = Comment(게시글_ID, "너무 재밌어요")
        commentRepository.save(newComment)

        val comment = commentRepository.findByIdOrNull(newComment.id)!!
        val updateRequest = CommentUpdateCommand("너무 재미없어요")

        assertThrows<IllegalStateException> { commentCommandService.update(comment.id!!, updateRequest, 2L) }
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
