package com.sat.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.BindException;

import java.util.List;

public record ErrorResponse(
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<BindErrorResponse> errors
) {
    public static ErrorResponse of(Exception e) {
        return new ErrorResponse(e.getMessage(), null);
    }

    public static ErrorResponse of(BindException e) {
        List<BindErrorResponse> bindErrorResponses = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(BindErrorResponse::of)
                .toList();
        return new ErrorResponse("바인딩 에러", bindErrorResponses);
    }
}
