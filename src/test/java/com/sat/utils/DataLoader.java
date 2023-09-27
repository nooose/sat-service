package com.sat.utils;

import com.sat.member.domain.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.sat.member.MemberFixtures.일반_사용자;

@Component
public class DataLoader {
    private final MemberRepository memberRepository;

    public DataLoader(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void loadData() {
        memberRepository.save(일반_사용자);
    }
}
