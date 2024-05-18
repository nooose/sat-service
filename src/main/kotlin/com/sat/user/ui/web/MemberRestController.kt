package com.sat.user.ui.web

import com.sat.board.application.query.ArticleQueryService
import com.sat.board.application.query.CommentQueryService
import com.sat.board.application.query.dto.CommentWithArticle
import com.sat.board.application.query.dto.LikedArticleSimpleQuery
import com.sat.board.domain.dto.query.ArticleWithCount
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import com.sat.user.application.command.MemberCommandService
import com.sat.user.application.command.dto.MemberUpdateCommand
import com.sat.user.application.query.MemberQueryService
import com.sat.user.application.query.PointQueryService
import com.sat.user.application.query.dto.MemberInformation
import com.sat.user.application.query.dto.MemberSimpleQuery
import com.sat.user.application.query.dto.MyPointQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
    fun articles(@LoginMember member: AuthenticatedMember): List<ArticleWithCount> {
        return articleQueryService.getAll(member.id)
    }

    @GetMapping("/user/comments")
    fun getMyComments(@LoginMember member: AuthenticatedMember): List<CommentWithArticle> {
        return commentQueryService.getComments(member.id)
    }

    // TODO: 테스트코드 작성
    @GetMapping("/user/points")
    fun points(@LoginMember member: AuthenticatedMember): List<MyPointQuery> {
        return pointQueryService.getPoints(member.id)
    }

    // TODO: 테스트코드 작성
    @GetMapping("/user/likes")
    fun likes(@LoginMember member: AuthenticatedMember): List<LikedArticleSimpleQuery> {
        return articleQueryService.getLikedArticles(member.id)
    }
}
