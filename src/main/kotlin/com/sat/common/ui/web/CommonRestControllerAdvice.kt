package com.sat.common.ui.web

import com.sat.common.domain.exception.NotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestControllerAdvice

val log = KotlinLogging.logger { }

@RestControllerAdvice
class CommonRestControllerAdvice {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        val editor = StringTrimmerEditor(true)
        binder.registerCustomEditor(String::class.java, editor)
    }

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun error(e: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(ErrorResponse(e.message ?: ""))
    }

    @ExceptionHandler(NotFoundException::class)
    fun error(e: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(e.message))
    }

    @ExceptionHandler(Exception::class)
    fun error(e: Exception): ResponseEntity<ErrorResponse> {
        log.error { e.message }
        return ResponseEntity.internalServerError().body(ErrorResponse("에러 발생"))
    }
}
