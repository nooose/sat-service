package com.sat.auth.application;

import com.sat.auth.domain.MemberRole;
import com.sat.auth.domain.MemberRoleRepository;
import com.sat.member.domain.Member;
import com.sat.member.domain.MemberId;
import com.sat.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberRoleService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional
    public MemberRole joinIfNotExists(String id, String name) {
        MemberId memberId = MemberId.of(id);
        return memberRoleRepository.findByMemberId(memberId)
                .orElseGet(() -> {
                    memberRepository.save(new Member(memberId, name));
                    return memberRoleRepository.save(new MemberRole(memberId));
                });

    }
}
