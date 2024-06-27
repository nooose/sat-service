package com.sat.user.command.application

import com.sat.user.command.domain.member.*
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

val log = KotlinLogging.logger { }

@Transactional
@Service
class MemberLoginService(
    private val memberRepository: MemberRepository,
    private val roleRepository: RoleRepository,
    private val loginHistoryRepository: LoginHistoryRepository,
) {

    fun login(name: String, email: String): Member {
        return memberRepository.findByEmail(email) ?: saveMember(name, email)
    }

    fun createLoginHistory(id: Long, dateTime: LocalDateTime) {
        val history = LoginHistory(id, dateTime)
        loginHistoryRepository.save(history)
        log.info { "로그인 이력 저장 - $id $dateTime" }
    }

    private fun saveMember(name: String, email: String): Member {
        val defaultRole = roleRepository.findByType(RoleType.DEFAULT)
        val newMember = Member.new(name = name, email = email, defaultRole = defaultRole)
        return memberRepository.save(newMember)
    }
}
