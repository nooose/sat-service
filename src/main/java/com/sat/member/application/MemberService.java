package com.sat.member.application;

import com.sat.common.exception.DataNotFoundException;
import com.sat.member.application.dto.MemberResponse;
import com.sat.member.domain.MemberId;
import com.sat.member.domain.MemberRepository;
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
                .orElseThrow(() -> new DataNotFoundException("사용자를 찾을 수 없습니다. - " + principal));
    }
}
