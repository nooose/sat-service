package com.sat.user.command.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface LoginHistoryRepository : JpaRepository<LoginHistory, Long> {

    fun existsByLoginDateTimeAfter(today: LocalDateTime): Boolean
}
