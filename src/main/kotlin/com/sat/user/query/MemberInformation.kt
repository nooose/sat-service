package com.sat.user.query

import com.sat.security.AuthenticatedMember


data class MemberInformation(
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val avatar: String,
    val point: Int,
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
            )
        }
    }
}
