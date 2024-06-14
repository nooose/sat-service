package com.sat.common.ui.web

import com.sat.common.domain.exception.DuplicateException
import com.sat.common.domain.exception.NotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RestControllerAdvice

val log = KotlinLogging.logger { }

@RestControllerAdvice
class GlobalRestControllerAdvice {

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        val editor = StringTrimmerEditor(true)
        binder.registerCustomEditor(String::class.java, editor)
    }

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun error(e: RuntimeException): ResponseEntity<ErrorResponse<String>> {
        return ResponseEntity.badRequest().body(ErrorResponse(e.message ?: ""))
    }

    @ExceptionHandler(NotFoundException::class)
    fun error(e: NotFoundException): ResponseEntity<ErrorResponse<String>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(e.message))
    }

    @ExceptionHandler(DuplicateException::class)
    fun error(e: DuplicateException): ResponseEntity<ErrorResponse<String>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse(e.message))
    }

    @ExceptionHandler(BindException::class)
    fun error(e: BindException): ResponseEntity<ErrorResponse<List<BindErrorResponse>>> {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun error(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse<List<BindErrorResponse>>> {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e))
    }

    @ExceptionHandler(Exception::class)
    fun error(e: Exception): ResponseEntity<ErrorResponse<String>> {
        log.error { e.stackTraceToString() }
        return ResponseEntity.internalServerError().body(ErrorResponse("알 수 없는 에러가 발생했습니다."))
    }
}
