package com.sat.auth.domain;

import lombok.Getter;

public enum RoleType {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    @Getter
    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}
