package com.sat.user.domain.event

import java.time.LocalDateTime

data class LoginSuccessEvent(
    val id: Long,
    val dateTime: LocalDateTime,
)
