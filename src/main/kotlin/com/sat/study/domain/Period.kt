package com.sat.study.domain

import jakarta.persistence.Embeddable
import java.time.LocalDate

@Embeddable
data class Period(
    val startDate: LocalDate,
    val endDate: LocalDate,
) {

    init {
        require(startDate < endDate) { "종료일($endDate)이 시작일($startDate)보다 같거나 빠를 수 없습니다." }
    }
}
