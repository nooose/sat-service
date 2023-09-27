package com.sat.auth.config.login;

import lombok.Getter;

@Getter
public enum RoleType {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}
