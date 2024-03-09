package com.sat.common.ui.web

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.sat.common.domain.exception.DuplicateException
import com.sat.common.domain.exception.NotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.propertyeditors.StringTrimmerEditor
import org.springframework.dao.DataIntegrityViolationException
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

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun error(e: DataIntegrityViolationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse("데이터 정합성이 올바르지 않습니다."))
    }

    @ExceptionHandler(NotFoundException::class)
    fun error(e: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(e.message))
    }

    @ExceptionHandler(DuplicateException::class)
    fun error(e: DuplicateException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse(e.message))
    }

    @ExceptionHandler(BindException::class)
    fun error(e: BindException): ResponseEntity<List<BindErrorResponse>> {
        val errorResponses = e.bindingResult.fieldErrors.map { BindErrorResponse(it.field, it.defaultMessage ?: "") }
        return ResponseEntity.badRequest().body(errorResponses)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun error(e: HttpMessageNotReadableException): ResponseEntity<List<BindErrorResponse>> {
        val exception = e.cause as MismatchedInputException
        val responses = exception.path.map { BindErrorResponse(it.fieldName, "null 불가") }
        return ResponseEntity.badRequest().body(responses)
    }

    @ExceptionHandler(Exception::class)
    fun error(e: Exception): ResponseEntity<ErrorResponse> {
        log.error { e.message }
        return ResponseEntity.internalServerError().body(ErrorResponse("알 수 없는 에러가 발생했습니다."))
    }
}
