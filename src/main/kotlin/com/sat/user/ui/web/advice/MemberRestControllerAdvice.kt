package com.sat.user.ui.web.advice

import com.sat.common.ui.web.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class MemberRestControllerAdvice {

    @ExceptionHandler(ExpiredJwtException::class)
    fun error(e: ExpiredJwtException): ResponseEntity<ErrorResponse<String>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse("만료된 토큰입니다.", "EXPIRED"))
    }
}
