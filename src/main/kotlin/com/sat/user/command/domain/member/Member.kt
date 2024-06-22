package com.sat.user.command.domain.member

import com.sat.common.BaseEntity
import jakarta.persistence.Entity

@Entity
class Member(
    val name: String,
    var nickname: String,
    val email: String,
    id: Long = 0L,
) : BaseEntity(id) {

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
