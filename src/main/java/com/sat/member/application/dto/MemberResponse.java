package com.sat.member.application.dto;

import com.sat.member.domain.Member;

public record MemberResponse(
        Long id,
        String name
) {

    public static MemberResponse of(Member entity) {
        return new MemberResponse(
                entity.getId(),
                entity.getName().getValue()
        );
    }
}
