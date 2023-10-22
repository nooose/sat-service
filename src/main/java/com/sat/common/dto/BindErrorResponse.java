package com.sat.common.dto;

import org.springframework.validation.FieldError;

public record BindErrorResponse(
        String field,
        String message,
        String invalidValue
) {

    public static BindErrorResponse of(FieldError error) {
        return new BindErrorResponse(error.getField(), error.getDefaultMessage(), (String) error.getRejectedValue());
    }
}
