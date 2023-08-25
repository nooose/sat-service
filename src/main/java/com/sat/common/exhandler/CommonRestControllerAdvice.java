package com.sat.common.exhandler;

import com.sat.common.dto.ErrorResponse;
import com.sat.common.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class CommonRestControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> error(BindException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> error(DateTimeParseException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> error(DataNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e);
        return ResponseEntity.badRequest().body(errorResponse);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> error(IllegalArgumentException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
