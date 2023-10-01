package com.sat.auth.domain;

import com.sat.member.domain.MemberId;
import com.sat.member.domain.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class MemberRole {
    @Id @GeneratedValue
    private Long id;
    private MemberId memberId;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public MemberRole(MemberId memberId) {
        this.memberId = memberId;
        this.role = RoleType.MEMBER;
    }
}
