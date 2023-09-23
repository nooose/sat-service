package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Objects;

public abstract class AbstractDataSourceInfo {

    private final String url;
    private final String username;
    private final String password;
    private final HikariConfig hikari;

    AbstractDataSourceInfo(String url, String username, String password, HikariConfig hikari) {
        this.url = Objects.requireNonNull(url);
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNullElse(password, "");
        this.hikari = hikari;
    }

    public DataSource initializeDataSource(HikariConfig parentHikariConfig) {
        HikariConfig config = Objects.requireNonNullElse(hikari, parentHikariConfig);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setReadOnly(isReadOnly());
        config.setPoolName((config.isReadOnly() ? "Reader" : "Writer") + "DataSource");
        return new HikariDataSource(config);
    }

    abstract boolean isReadOnly();
}
