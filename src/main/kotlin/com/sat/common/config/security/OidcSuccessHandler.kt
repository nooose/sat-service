package com.sat.common.config.security

import com.sat.common.utils.event.Events
import com.sat.user.domain.event.LoginSuccessEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime

val log = KotlinLogging.logger { }

@Component
class OidcSuccessHandler(
    @Value("\${external.frontend.url}")
    private val frontendUrl: String,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val principal = authentication.principal as AuthenticatedMember
        response.status = HttpStatus.OK.value()
        response.sendRedirect(frontendUrl)
        Events.publish(LoginSuccessEvent(principal.id, LocalDateTime.now()))
    }
}
