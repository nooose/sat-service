package com.sat.user.command.application.dto

import jakarta.validation.constraints.NotBlank

data class LoginCommand(
    @field:NotBlank
    val accessToken: String,
)
