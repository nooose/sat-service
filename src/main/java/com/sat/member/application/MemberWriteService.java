package com.sat.member.application;

import com.sat.member.application.dto.MemberRequest;
import com.sat.member.application.dto.MemberResponse;
import com.sat.member.domain.Member;
import com.sat.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberWriteService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse join(MemberRequest request) {
        String name = request.name();
        memberRepository.findByName_Value(name).ifPresent(it -> {
            throw new IllegalArgumentException(name + "은(는) 중복된 이름입니다.");
        });
        Member newMember = new Member(name);
        memberRepository.save(newMember);
        return MemberResponse.of(newMember);
    }
}
