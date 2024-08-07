package com.sat.user.command.domain.member

import com.sat.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
class Member(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    var nickname: String,
    @Column(nullable = false)
    val email: String,
    @OneToOne @JoinColumn(name = "role_id", nullable = false)
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
