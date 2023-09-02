package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@ConfigurationProperties(prefix = "spring.datasource")
public record DataSourceProperties(
        Map<String, DataSourceGroup> groups,
        HikariConfig hikari
) {

    public DataSourceProperties(Map<String, DataSourceGroup> groups, HikariConfig hikari) {
        this.groups = groups;
        this.hikari = Objects.requireNonNullElse(hikari, new HikariConfig());
    }

    public record DataSourceGroup(
            boolean primary,
            ReaderDataSourceInfo reader,
            WriterDataSourceInfo writer
    ) {

        public DataSource initializeDataSource(String namePrefix, HikariConfig defaultHikariConfig) {
            if (isCluster()) {
                DataSource readerDataSource = reader.initializeDataSource(namePrefix, defaultHikariConfig);
                DataSource writerDataSource = writer.initializeDataSource(namePrefix, defaultHikariConfig);
                return new RoutingDataSource(readerDataSource, writerDataSource);
            }
            return getOne().initializeDataSource(namePrefix, defaultHikariConfig);
        }

        public boolean isCluster() {
            return reader != null && writer != null;
        }

        private AbstractDataSourceInfo getOne() {
            if (Optional.ofNullable(writer).isPresent()) {
                return writer;
            }
            return reader;
        }
    }
}
