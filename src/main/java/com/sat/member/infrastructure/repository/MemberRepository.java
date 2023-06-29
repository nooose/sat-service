package com.sat.member.infrastructure.repository;

import com.sat.member.domain.Member;
import com.sat.member.domain.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, MemberId> {
    Optional<Member> findByName_Value(String name);

    Optional<Member> findById(MemberId id);
}
