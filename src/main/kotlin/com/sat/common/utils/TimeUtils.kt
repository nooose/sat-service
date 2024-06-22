package com.sat.common.utils

import java.time.LocalDateTime
import java.time.LocalTime

fun LocalDateTime.toZeroTime(): LocalDateTime {
    return LocalDateTime.of(this.toLocalDate(), LocalTime.MIN)
}
