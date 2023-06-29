package com.sat.auth.application;

import com.sat.member.domain.Member;
import com.sat.member.domain.MemberId;
import com.sat.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberAccountService {

    private final MemberRepository memberRepository;

    public Member findOrCreate(String id) {
        return memberRepository.findById(new MemberId(id))
            .orElseGet(() -> memberRepository.save(new Member(id)));
    }
}
