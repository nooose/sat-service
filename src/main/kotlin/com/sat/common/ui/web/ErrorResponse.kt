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
            return ErrorResponse(errors, "BIND")
        }

        fun from(e: HttpMessageNotReadableException): ErrorResponse<List<BindErrorResponse>> {
            val errors = when (e.cause) {
                is MismatchedInputException -> (e.cause as MismatchedInputException).path.map {
                    BindErrorResponse(it.fieldName, "필수 값입니다.")
                }
                else -> listOf(BindErrorResponse("", "Json 규격이 올바르지 않습니다."))
            }
            return ErrorResponse(errors, "BIND")
        }
    }
}
