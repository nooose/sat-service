package com.sat.study;

import com.sat.member.domain.MemberId;

public class Host {
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
