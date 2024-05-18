package com.sat.user.application.command.dto

import jakarta.validation.constraints.Size

data class MemberUpdateCommand(
    @Size(min = 2, max = 15)
    val nickname: String,
)
