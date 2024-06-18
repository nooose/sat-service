package com.sat.user.query

import com.sat.common.utils.findByIdOrThrow
import com.sat.user.command.domain.member.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberQueryService(
    private val memberRepository: MemberRepository,
) {

    fun get(): List<MemberSimpleQuery> {
        return memberRepository.findAll().map { MemberSimpleQuery.from(it) }
    }

    fun getInfo(memberId: Long): MemberSimpleQuery {
        val member = memberRepository.findByIdOrThrow(memberId){ "존재하지 않는 회원입니다 - $memberId" }
        return MemberSimpleQuery.from(member)
    }
}
