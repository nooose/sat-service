package com.sat.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class MemberId implements Serializable {

    @Getter
    @Column(name = "member_id")
    private String id;

    public MemberId(String id) {
        this.id = id;
    }
}



