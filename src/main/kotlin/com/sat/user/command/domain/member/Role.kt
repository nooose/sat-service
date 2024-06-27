package com.sat.user.command.domain.member

import com.sat.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Role(
    @Enumerated(EnumType.STRING)
    val type: RoleType,
    id: Long = 0L,
) : BaseEntity(id)
