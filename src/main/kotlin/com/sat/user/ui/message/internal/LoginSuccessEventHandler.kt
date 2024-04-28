package com.sat.user.ui.message.internal

import com.sat.common.config.security.AuthenticatedMember
import com.sat.user.application.MemberLoginService
import com.sat.user.application.PointService
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Component
class LoginSuccessEventHandler(
    private val memberLoginService: MemberLoginService,
    private val pointService: PointService,
) {

    @Transactional
    @EventListener(AuthenticationSuccessEvent::class)
    fun handle(event: AuthenticationSuccessEvent) {
        val authenticatedMember = event.authentication.principal as AuthenticatedMember
        val now = LocalDateTime.now()
        pointService.dailyPointAward(authenticatedMember.id, now.toLocalDate())
        memberLoginService.createLoginHistory(authenticatedMember.id, now)
    }
}
