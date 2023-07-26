package com.sat.study.domain;

import com.sat.member.domain.MemberId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Host {

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "host_id"))
    })
    private MemberId id;

    public Host(MemberId id) {
        this.id = id;
    }

    public boolean isOwner(MemberId memberId) {
        return id.equals(memberId);
    }

    public MemberId getId() {
        return id;
    }
}
