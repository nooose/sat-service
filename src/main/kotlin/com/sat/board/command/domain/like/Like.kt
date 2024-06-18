package com.sat.board.command.domain.like

import com.sat.common.domain.AuditingFields
import jakarta.persistence.*

@Table(name = "LIKES")
@Entity
data class Like(
    val articleId: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : AuditingFields()
