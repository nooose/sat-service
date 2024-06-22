package com.sat.user.command.application

import com.sat.security.principal
import com.sat.security.replacePrincipal
import com.sat.user.command.domain.member.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberCommandService(
    private val memberRepository: MemberRepository,
) {

    fun update(id: Long, command: MemberUpdateCommand) {
        val member = memberRepository.findByIdOrNull(id) ?: throw IllegalStateException("사용자를 찾을 수 없습니다. - $id")
        val principal = SecurityContextHolder.getContext().principal()
        member.updateNickname(command.nickname)
        val updatedPrincipal = principal.copy(nickname = command.nickname)
        SecurityContextHolder.getContext().replacePrincipal(updatedPrincipal)
    }
}
