package com.sat.member.application;

import com.sat.member.application.dto.MemberResponse;
import com.sat.member.domain.MemberId;
import com.sat.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberReadService {

    private final MemberRepository memberRepository;

    public MemberResponse findBy(String principal) {
        return memberRepository.findById(new MemberId(principal))
                .map(MemberResponse::of)
                .orElseThrow();
    }
}
