package com.sat.common.ui.web

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class ErrorResponse <T> (
    val error: T,
    val code: String? = null,
) {

    companion object {
        fun from(e: BindException): ErrorResponse<List<BindErrorResponse>> {
            val errors = e.bindingResult.fieldErrors.map {
                BindErrorResponse(it.field, it.defaultMessage ?: "")
            }
            return ErrorResponse(errors)
        }

        fun from(e: HttpMessageNotReadableException): ErrorResponse<List<BindErrorResponse>> {
            val mismatchedInputException = e.cause as MismatchedInputException
            val errors = mismatchedInputException.path.map {
                BindErrorResponse(it.fieldName, "Non-nullable")
            }
            return ErrorResponse(errors)
        }
    }
}
