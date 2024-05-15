package com.sat.user.ui.web

import com.sat.board.application.query.ArticleQueryService
import com.sat.board.application.query.CommentQueryService
import com.sat.board.domain.dto.query.ArticleWithCount
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import com.sat.user.application.query.MemberQueryService
import com.sat.user.application.query.PointQueryService
import com.sat.user.application.query.dto.MemberInformation
import com.sat.user.application.query.dto.MemberSimpleQuery
import com.sat.user.domain.Point
import com.sat.user.domain.dto.CommentDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberRestController(
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

    @GetMapping("/user/members")
    fun members(): List<MemberSimpleQuery> {
        return memberQueryService.get()
    }

    // TODO: 테스트코드 작성
    @GetMapping("/user/articles")
    fun articles(@LoginMember member: AuthenticatedMember): List<ArticleWithCount> {
        return articleQueryService.getAll(member.id)
    }

    // TODO: 테스트코드 작성
    @GetMapping("/user/comments")
    fun getMyComments(@LoginMember member: AuthenticatedMember): List<CommentDto>{
        return commentQueryService.getComments(member.id)
    }

    // TODO: 화면 기획 나온 후 API 변경 예정
    @GetMapping("/user/members/me/points")
    fun points(@LoginMember member: AuthenticatedMember): List<Point> {
        return pointQueryService.getPoints(member.id)
    }
}
