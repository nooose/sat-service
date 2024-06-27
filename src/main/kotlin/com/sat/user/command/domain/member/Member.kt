package com.sat.user.command.domain.member

import com.sat.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
class Member(
    val name: String,
    var nickname: String,
    val email: String,
    @OneToOne @JoinColumn(name = "role_id")
    val role: Role,
    id: Long = 0L,
) : BaseEntity(id) {

    fun updateNickname(nickname: String) {
        require(nickname.isNotBlank()) { "닉네임은 공백일 수 없습니다." }
        this.nickname = nickname
    }

    companion object {
        fun new(name: String, email: String, defaultRole: Role): Member {
            return Member(
                name = name,
                nickname = name,
                email = email,
                role = defaultRole,
            )
        }
    }
}
