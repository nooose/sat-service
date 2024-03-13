package com.sat.user.application.dto.command

import jakarta.validation.constraints.NotBlank

data class LoginCommand(
    @field:NotBlank
    val accessToken: String,
)
