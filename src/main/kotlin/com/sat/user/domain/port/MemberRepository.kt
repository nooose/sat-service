package com.sat.user.domain.port

import com.sat.user.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?

    @Query("""
        select m from Member m
        where m.id in (:ids)
    """)
    fun findByIdContains(ids: List<Long>): List<Member>
}
