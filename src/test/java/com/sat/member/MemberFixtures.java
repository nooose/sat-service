package com.sat.member;

import com.sat.member.domain.Member;
import com.sat.member.domain.MemberId;

public class MemberFixtures {
    public static Member 일반_사용자 = new Member(new MemberId("1234"), "홍길동");
}
