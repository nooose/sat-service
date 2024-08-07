package com.sat.user.command.domain.member

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class LoginHistory(
    @Column(nullable = false)
    val memberId: Long,
    @Column(nullable = false)
    val loginDateTime: LocalDateTime,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
)
