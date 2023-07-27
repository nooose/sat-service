package com.sat.member.application;

import com.sat.member.application.dto.MemberResponse;
import com.sat.member.domain.Member;
import com.sat.member.domain.MemberId;
import com.sat.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse findBy(String principal) {
        return memberRepository.findById(MemberId.of(principal))
                .map(MemberResponse::of)
                .orElseThrow();
    }

    @Transactional
    public void joinIfNotExists(String id, String name) {
        MemberId memberId = MemberId.of(id);
        if (memberRepository.existsById(memberId)) {
            return;
        }
        memberRepository.save(new Member(memberId, name));
    }
}
