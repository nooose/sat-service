package com.sat.board.ui.web.advice

import com.sat.board.domain.exception.ChildExistsException
import com.sat.common.ui.web.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BoardRestControllerAdvice {

    @ExceptionHandler(ChildExistsException::class)
    fun error(e: ChildExistsException): ResponseEntity<ErrorResponse<String>> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse(e.message))
    }
}
