package com.sat.auth.application;

import com.sat.member.domain.Member;
import com.sat.member.domain.MemberId;
import com.sat.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberAccountWriteService {

    private final MemberRepository memberRepository;

    public void join(String id, String name) {
        memberRepository.save(new Member(new MemberId(id), name));
    }
}
