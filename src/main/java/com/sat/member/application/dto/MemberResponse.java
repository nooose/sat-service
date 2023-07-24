package com.sat.member.application.dto;

import com.sat.member.domain.Member;

public record MemberResponse(
    String id,
    String name
) {

    public static MemberResponse of(Member entity) {
        return new MemberResponse(entity.fetchId(), entity.getName().getValue());
    }
}
