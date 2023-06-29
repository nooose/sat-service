package com.sat.auth.application;

import com.sat.member.domain.Member;
import com.sat.member.domain.MemberId;
import com.sat.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberAccountService {

    private final MemberRepository memberRepository;

    public Member findOrCreate(String id) {
        return memberRepository.findById(new MemberId(id))
            .orElseGet(() -> memberRepository.save(new Member(id)));
    }
}
