package com.sat.user.application

import com.sat.user.application.dto.query.MemberSimpleQuery
import com.sat.user.domain.port.MemberRepository
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
}
