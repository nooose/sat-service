package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;

import javax.sql.DataSource;
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

    public DataSource initializeDataSource(String namePrefix, HikariConfig defaultHikariConfig) {
        HikariConfig config = Objects.requireNonNullElse(hikari, defaultHikariConfig);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setReadOnly(isReadOnly());
        config.setPoolName(namePrefix + (config.isReadOnly() ? "Reader" : "Writer"));
        return new HikariDataSource(config);
    }

    abstract public boolean isReadOnly();
}
