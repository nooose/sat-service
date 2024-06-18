package com.sat.user.command.application.dto

import jakarta.validation.constraints.Size

data class MemberUpdateCommand(
    @Size(min = 2, max = 15)
    val nickname: String,
)
