package com.sat.board.domain

import com.sat.common.domain.AuditingFields
import jakarta.persistence.*

@Table(name = "LIKES")
@Entity
data class Like(
    val articleId: Long,
    val memberId: Long,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditingFields()
