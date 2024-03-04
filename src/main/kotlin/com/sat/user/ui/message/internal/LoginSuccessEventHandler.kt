package com.sat.user.ui.message.internal

import com.sat.user.application.MemberLoginService
import com.sat.user.application.PointService
import com.sat.user.domain.event.LoginSuccessEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate


@Component
class LoginSuccessEventHandler(
    private val memberLoginService: MemberLoginService,
    private val pointService: PointService,
) {

    @Transactional
    @EventListener(LoginSuccessEvent::class)
    fun handle(event: LoginSuccessEvent) {
        pointService.dailyPointAward(event.id, LocalDate.now())
        memberLoginService.createLoginHistory(event.id, event.dateTime)
    }
}
