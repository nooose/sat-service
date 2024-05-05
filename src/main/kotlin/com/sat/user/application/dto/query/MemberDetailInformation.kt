package com.sat.user.application.dto.query

import com.sat.common.config.security.AuthenticatedMember


data class MemberDetailInformation(
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val avatar: String,
    val point: Int,
) {
    companion object {
        fun of(member: AuthenticatedMember, point: Int): MemberDetailInformation {
            return MemberDetailInformation(
                id = member.id,
                name = member.name,
                nickname = member.nickname,
                email = member.email,
                avatar = member.avatar,
                point = point,
            )
        }
    }
}
