package com.sat.user.ui.web

import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import com.sat.user.application.MemberQueryService
import com.sat.user.application.PointService
import com.sat.user.application.dto.query.MemberDetailInformation
import com.sat.user.application.dto.query.MemberSimpleQuery
import com.sat.user.domain.Point
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberRestController(
    private val memberQueryService: MemberQueryService,
    private val pointService: PointService,
) {

    @GetMapping("/user/members/me")
    fun me(@LoginMember member: AuthenticatedMember): MemberDetailInformation {
        val point = pointService.getPoint(member.id)
        return MemberDetailInformation.of(member, point)
    }

    @GetMapping("/user/members")
    fun members(): List<MemberSimpleQuery> {
        return memberQueryService.get()
    }

    @GetMapping("/user/members/me/pointHistory")
    fun myPointHistory(@LoginMember member: AuthenticatedMember): List<Point> {
        return pointService.getPointHistory(member.id)
    }
}
