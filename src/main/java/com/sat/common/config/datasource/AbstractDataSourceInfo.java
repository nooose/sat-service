package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class AbstractDataSourceInfo {
    private final String url;
    private final String username;
    private final String password;
    private final HikariConfig hikari;

    public AbstractDataSourceInfo(String url, String username, String password, HikariConfig hikari) {
        this.url = Objects.requireNonNull(url);
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNullElse(password, "");
        this.hikari = hikari;
    }

    abstract public boolean isReadOnly();
}
