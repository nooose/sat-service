package com.sat.auth.domain;

import com.sat.member.domain.RoleType;
import com.sat.member.domain.MemberId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "member_id"))
    })
    private MemberId memberId;
    @Enumerated
    private RoleType role;

    public MemberRole(MemberId memberId) {
        this.memberId = memberId;
        this.role = RoleType.MEMBER;
    }
}
