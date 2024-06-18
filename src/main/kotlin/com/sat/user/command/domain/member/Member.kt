package com.sat.user.command.domain.member

import com.sat.common.domain.AuditingFields
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    val name: String,
    var nickname: String,
    val email: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : AuditingFields() {

    fun updateNickname(nickname: String) {
        require(nickname.isNotBlank()) { "닉네임은 공백일 수 없습니다." }
        this.nickname = nickname
    }

    companion object {
        fun new(name: String, email: String): Member {
            return Member(name = name, nickname = name, email = email)
        }
    }
}
