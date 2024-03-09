package com.sat.common.ui.web

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class BindErrorResponse(
    val field: String,
    val message: String,
)
