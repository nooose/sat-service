package com.sat.user.domain.port.repository

import com.sat.user.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
}
