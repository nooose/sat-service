package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Optional;

@ConfigurationProperties(prefix = "spring.datasource")
public record DataSourceProperties(
    @NestedConfigurationProperty
    ReaderDataSourceInfo reader,
    @NestedConfigurationProperty
    WriterDataSourceInfo writer,
    HikariConfig hikari
) {

    public DataSourceProperties(ReaderDataSourceInfo reader, WriterDataSourceInfo writer, HikariConfig hikari) {
        this.reader = reader;
        this.writer = writer;
        this.hikari = Objects.requireNonNullElse(hikari, new HikariConfig());
    }

    public DataSource initializeDataSource() {
        if (isCluster()) {
            DataSource readerDataSource = reader.initializeDataSource(hikari);
            DataSource writerDataSource = writer.initializeDataSource(hikari);
            return new RoutingDataSource(readerDataSource, writerDataSource);
        }
        return getOne().initializeDataSource(hikari);
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
