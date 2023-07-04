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
        this(null, new Name(name));
    }

    public Member(MemberId id, String name) {
        this(id, new Name(name));
    }

    public Member(MemberId id, Name name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id.getId();
    }
}
