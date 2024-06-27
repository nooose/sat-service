package com.sat.user.query

import com.sat.security.AuthenticatedMember
import com.sat.user.command.domain.member.RoleType


data class MemberInformation(
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val avatar: String,
    val point: Int,
    val isAdmin: Boolean,
) {
    companion object {
        fun of(member: AuthenticatedMember, point: Int): MemberInformation {
            return MemberInformation(
                id = member.id,
                name = member.name,
                nickname = member.nickname,
                email = member.email,
                avatar = member.avatar,
                point = point,
                isAdmin = member.roles.contains(RoleType.ADMIN)
            )
        }
    }
}
