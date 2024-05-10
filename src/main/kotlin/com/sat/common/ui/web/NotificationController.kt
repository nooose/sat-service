package com.sat.common.ui.web

import com.sat.common.application.NotificationService
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import com.sat.common.domain.dto.query.NotificationQuery
import com.sat.common.infrastructure.EmitterRepository
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
class NotificationController(
    private val notificationService: NotificationService,
    private val emitterRepository: EmitterRepository,
) {
    @GetMapping("/notification:subscribe", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(@LoginMember member: AuthenticatedMember): SseEmitter {
        return notificationService.connect(member.id)
    }

    @GetMapping("/notification")
    fun test(@AuthenticationPrincipal member: AuthenticatedMember) {
        val sseEmitter = emitterRepository.findBy(member.id)!!
        val data = SseEmitter.event()
            .name("notification")
            .data(NotificationQuery("제목", "내용"))
            sseEmitter.send(data)
    }
}
