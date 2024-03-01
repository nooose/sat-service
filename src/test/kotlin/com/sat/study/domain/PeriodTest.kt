package com.sat.study.domain

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

@DisplayName(value = "도메인 - 기간 테스트")
class PeriodTest {

    @Test
    fun `종료일보다 시작일보다 빠르면 예외를 던진다`() {
        val now = LocalDate.of(2024, 1, 10)
        val message = assertThrows<IllegalArgumentException> {
            Period(now, now.minusDays(1))
        }.message
        println(message)
    }
}
