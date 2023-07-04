package com.sat.auth.application;

import com.sat.member.domain.MemberId;
import com.sat.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberAccountReadService {

    private final MemberRepository memberRepository;

    public boolean existById(String id) {
        return memberRepository.existsById(new MemberId(id));
    }
}
