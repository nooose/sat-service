package com.sat.user.command.domain.member

import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByType(roleType: RoleType): Role
}
