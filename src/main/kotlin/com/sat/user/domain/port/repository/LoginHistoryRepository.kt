package com.sat.user.domain.port.repository

import com.sat.user.domain.LoginHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface LoginHistoryRepository : JpaRepository<LoginHistory, Long> {

    fun existsByLoginDateTimeAfter(today: LocalDateTime): Boolean
}
