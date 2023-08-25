package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.Objects;

public class DataSourceFactory {
    public static DataSource generateDataSource(String key, DataSourceProperties.FlexibleDataSourceInfo flexibleDataSourceInfo, HikariConfig defaultHikariConfig) {
        if (flexibleDataSourceInfo.isCluster()) {
            HikariConfig readerConfig = setHikariConfig(flexibleDataSourceInfo.reader(), defaultHikariConfig);
            HikariDataSource reader = hikariDataSource(key, readerConfig);
            HikariConfig writerConfig = setHikariConfig(flexibleDataSourceInfo.writer(), defaultHikariConfig);
            HikariDataSource writer = hikariDataSource(key, writerConfig);
            return new RoutingDataSource(reader, writer);
        }

        HikariConfig config = setHikariConfig(flexibleDataSourceInfo.getOne(), defaultHikariConfig);
        return hikariDataSource(key, config);
    }

    private static HikariConfig setHikariConfig(AbstractDataSourceInfo info, HikariConfig defaultHikariConfig) {
        HikariConfig config = Objects.requireNonNullElse(info.getHikari(), defaultHikariConfig);
        config.setJdbcUrl(info.getUrl());
        config.setUsername(info.getUsername());
        config.setPassword(info.getPassword());
        config.setReadOnly(info.isReadOnly());
        return config;
    }

    private static HikariDataSource hikariDataSource(String name, HikariConfig config) {
        config.setPoolName(name + (config.isReadOnly() ? "Reader" : "Writer"));
        return new HikariDataSource(config);
    }
}
