package com.sat.user.application.command.dto

import jakarta.validation.constraints.NotBlank

data class LoginCommand(
    @field:NotBlank
    val accessToken: String,
)
