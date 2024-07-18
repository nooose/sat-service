package com.sat.user.ui.web

import com.sat.board.query.ArticleWithCountQuery
import com.sat.common.CursorRequest
import com.sat.common.PageCursor
import com.sat.security.AuthenticatedMember
import com.sat.security.LoginMember
import com.sat.user.command.application.MemberCommandService
import com.sat.user.command.application.MemberUpdateCommand
import com.sat.user.query.*
import org.springframework.web.bind.annotation.*

@RestController
class MemberRestController(
    private val memberCommandService: MemberCommandService,
    private val memberQueryService: MemberQueryService,
    private val pointQueryService: PointQueryService,
    private val boardQueryService: BoardQueryService,
) {

    @GetMapping("/user/members/me")
    fun me(@LoginMember member: AuthenticatedMember): MemberInformation {
        val point = pointQueryService.getPoint(member.id)
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
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.DEFAULT,
    ): List<ArticleWithCountQuery> {
        // TODO: 커서 기능 추가
        return boardQueryService.getArticles(member.id)
    }

    @GetMapping("/user/comments")
    fun getMyComments(
        @LoginMember member: AuthenticatedMember,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.DEFAULT,
    ): PageCursor<List<CommentWithArticleQuery>> {
        return boardQueryService.getComments(member.id, cursorRequest)
    }

    @GetMapping("/user/points")
    fun points(
        @LoginMember member: AuthenticatedMember,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.DEFAULT,
    ): PageCursor<List<MyPointQuery>> {
        return pointQueryService.getPoints(member.id, cursorRequest)
    }

    @GetMapping("/user/likes")
    fun likes(
        @LoginMember member: AuthenticatedMember,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.DEFAULT,
    ): PageCursor<List<LikedArticleSimpleQuery>> {
        return boardQueryService.getLikedArticles(member.id, cursorRequest)
    }

    @GetMapping("/user/members/{memberId}")
    fun getInfo(@PathVariable memberId: Long): MemberSimpleQuery {
        return memberQueryService.getInfo(memberId)
    }

    @GetMapping("/user/members/{memberId}/articles")
    fun getArticles(@PathVariable memberId: Long): List<ArticleWithCountQuery> {
        // TODO: 커서 기능 추가
        return boardQueryService.getArticles(memberId)
    }

    @GetMapping("/user/members/{memberId}/comments")
    fun getComments(
        @PathVariable memberId: Long,
        @ModelAttribute cursorRequest: CursorRequest = CursorRequest.DEFAULT,
    ): PageCursor<List<CommentWithArticleQuery>> {
        return boardQueryService.getComments(memberId, cursorRequest)
    }

    //todo: api 만 만들어놓음
    @GetMapping("/user/point-ranking")
    fun getPointRanking(): List<PointQuery> {
        return pointQueryService.getPointRanking()
    }
}
