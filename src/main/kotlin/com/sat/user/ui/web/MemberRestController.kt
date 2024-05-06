package com.sat.user.ui.web

import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import com.sat.user.application.MemberQueryService
import com.sat.user.application.PointQueryService
import com.sat.user.application.dto.query.MemberInformation
import com.sat.user.application.dto.query.MemberSimpleQuery
import com.sat.user.domain.Point
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberRestController(
    private val memberQueryService: MemberQueryService,
    private val pointQueryService: PointQueryService
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

    // TODO: 화면 기획 나온 후 API 변경 예정
    @GetMapping("/user/members/me/points")
    fun points(@LoginMember member: AuthenticatedMember): List<Point> {
        return pointQueryService.getPoints(member.id)
    }
}
