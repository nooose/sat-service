package com.sat.user.ui.web

import com.sat.board.application.query.ArticleQueryService
import com.sat.board.application.query.CommentQueryService
import com.sat.board.application.query.dto.CommentWithArticle
import com.sat.board.domain.dto.query.ArticleWithCount
import com.sat.board.domain.dto.query.LikedArticleSimpleQuery
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.user.application.command.MemberCommandService
import com.sat.user.application.command.dto.MemberUpdateCommand
import com.sat.user.application.query.MemberQueryService
import com.sat.user.application.query.PointQueryService
import com.sat.user.application.query.dto.MemberInformation
import com.sat.user.application.query.dto.MemberSimpleQuery
import com.sat.user.application.query.dto.MyPointQuery
import org.springframework.web.bind.annotation.*

@RestController
class MemberRestController(
    private val memberCommandService: MemberCommandService,
    private val memberQueryService: MemberQueryService,
    private val pointQueryService: PointQueryService,
    private val articleQueryService: ArticleQueryService,
    private val commentQueryService: CommentQueryService,
) {

    @GetMapping("/user/members/me")
    fun me(@LoginMember member: AuthenticatedMember): MemberInformation {
        val point = pointQueryService.getTotalPoint(member.id)
        return MemberInformation.of(member, point)
    }

    @PutMapping("/user/members/me")
    fun update(
        @LoginMember member: AuthenticatedMember,
        @RequestBody command: MemberUpdateCommand,
    ) {
        memberCommandService.update(member.id, command)
    }

    @GetMapping("/user/members")
    fun members(): List<MemberSimpleQuery> {
        return memberQueryService.get()
    }

    @GetMapping("/user/articles")
    fun articles(
        @LoginMember member: AuthenticatedMember,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.default(),
    ): List<ArticleWithCount> {
        // TODO: 커서 기능 추가
        return articleQueryService.getAll(member.id)
    }

    @GetMapping("/user/comments")
    fun getMyComments(
        @LoginMember member: AuthenticatedMember,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.default(),
    ): PageCursor<List<CommentWithArticle>> {
        return commentQueryService.getComments(member.id, cursorRequest)
    }

    @GetMapping("/user/points")
    fun points(
        @LoginMember member: AuthenticatedMember,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.default(),
    ): PageCursor<List<MyPointQuery>> {
        return pointQueryService.getPoints(member.id, cursorRequest)
    }

    @GetMapping("/user/likes")
    fun likes(
        @LoginMember member: AuthenticatedMember,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.default(),
    ): PageCursor<List<LikedArticleSimpleQuery>> {
        return articleQueryService.getLikedArticles(member.id, cursorRequest)
    }

    @GetMapping("/user/members/{memberId}")
    fun findById(@PathVariable memberId: Long): MemberSimpleQuery {
        return memberQueryService.findById(memberId)
    }

    @GetMapping("/user/members/{memberId}/articles")
    fun getYourArticles(@PathVariable memberId: Long): List<ArticleWithCount> {
        // TODO: 커서 기능 추가
        return articleQueryService.getAll(memberId)
    }

    @GetMapping("/user/members/{memberId}/comments")
    fun getYourComments(
        @PathVariable memberId: Long,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.default(),
    ): PageCursor<List<CommentWithArticle>> {
        return commentQueryService.getComments(memberId, cursorRequest)
    }
}
