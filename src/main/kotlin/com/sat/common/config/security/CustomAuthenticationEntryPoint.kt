package com.sat.common.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.sat.common.ui.web.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val writer = response.writer
        response.status = HttpStatus.UNAUTHORIZED.value()
        writer.println(objectMapper.writeValueAsString(ErrorResponse(authException.message)))
    }
}
