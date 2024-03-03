package com.sat.user.application

import com.sat.user.domain.Member
import com.sat.user.domain.port.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberLoginService(
    private val memberRepository: MemberRepository,
) {

    fun login(name: String, email: String): Member {
        return memberRepository.findByEmail(email) ?: saveMember(name, email)
    }

    private fun saveMember(name: String, email: String): Member {
        val newMember = Member(name = name, nickname = name, email = email)
        memberRepository.save(newMember)
        return newMember
    }
}
