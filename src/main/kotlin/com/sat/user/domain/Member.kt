package com.sat.user.domain

import com.sat.common.domain.AuditingFields
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(
    val name: String,
    val nickname: String,
    val email: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditingFields()
