package com.sat.user.command.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface MemberRepository : JpaRepository<Member, Long> {
    @Query(
        """
            select m from Member m
            join fetch m.role
            where m.email = :email
        """
    )
    fun findByEmail(@Param("email") email: String): Member?
}
