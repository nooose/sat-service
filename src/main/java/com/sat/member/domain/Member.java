package com.sat.member.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id @EmbeddedId
    private MemberId id;

    @Embedded
    private Name name;

    public Member(String name) {
        this.name = new Name(name);
    }

    public String getId() {
        return id.getId();
    }
}
