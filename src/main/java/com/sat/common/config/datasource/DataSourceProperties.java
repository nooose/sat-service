package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@ConfigurationProperties(prefix = "spring.datasource")
public record DataSourceProperties(
    Map<String, FlexibleDataSourceInfo> groups,
    HikariConfig hikari
) {
    public DataSourceProperties(Map<String, FlexibleDataSourceInfo> groups, HikariConfig hikari) {
        this.groups = groups;
        this.hikari = Objects.requireNonNullElse(hikari, new HikariConfig());
    }

    public record FlexibleDataSourceInfo(
        Boolean primary,
        ReaderDataSourceInfo reader,
        WriterDataSourceInfo writer
    ) {

        public FlexibleDataSourceInfo(Boolean primary, ReaderDataSourceInfo reader, WriterDataSourceInfo writer) {
            this.primary = Objects.requireNonNullElse(primary, false);
            this.reader = reader;
            this.writer = writer;
        }

        public boolean isCluster() {
            return reader != null && writer != null;
        }

        public AbstractDataSourceInfo getOne() {
            if (Optional.ofNullable(writer).isPresent()) {
                return writer;
            }
            return reader;
        }
    }
}
