package com.sat.auth.domain;

import com.sat.member.domain.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    Optional<MemberRole> findByMemberId(MemberId memberId);
}
