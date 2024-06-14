package com.sat.common.ui.web

import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import com.sat.event.application.NotificationProcessor
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Controller
class NotificationController(
    private val processor: NotificationProcessor,
) {
    @GetMapping("/notification:subscribe", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(@LoginMember member: AuthenticatedMember): SseEmitter {
        return processor.connect(member.id)
    }
}
