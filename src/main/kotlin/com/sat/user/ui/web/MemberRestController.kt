package com.sat.user.ui.web

import com.sat.user.application.MemberQueryService
import com.sat.user.application.dto.query.MemberSimpleQuery
import com.sat.user.domain.LoginMemberInfo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberRestController(
    private val memberQueryService: MemberQueryService,
) {

    @GetMapping("/user/members/me")
    fun me(@LoginMember member: LoginMemberInfo): LoginMemberInfo {
        return member
    }

    @GetMapping("/user/members")
    fun members(): List<MemberSimpleQuery> {
        return memberQueryService.get()
    }
}
