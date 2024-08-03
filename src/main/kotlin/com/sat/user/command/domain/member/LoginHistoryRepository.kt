package com.sat.user.command.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface LoginHistoryRepository : JpaRepository<LoginHistory, Long> {

    fun existsByLoginDateTimeAfterAndMemberId(today: LocalDateTime, memberId: Long): Boolean
}
