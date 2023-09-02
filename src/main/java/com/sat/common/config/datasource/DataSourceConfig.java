package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class DataSourceConfig {

    private static final String DATA_SOURCE_SUFFIX = "DataSource";

    private final DataSourceProperties dataSourceProperties;
    private final GenericApplicationContext applicationContext;

    @Bean
    public DataSource init() {
        DataSource primaryDataSource = null;
        for (var entry: dataSourceProperties.groups().entrySet()) {
            String key = entry.getKey();
            HikariConfig defaultHikariConfig = dataSourceProperties.hikari();
            DataSource dataSource = entry.getValue().initializeDataSource(key, defaultHikariConfig);
            DataSource dataSourceBean = registerDataSource(entry, dataSource);
            if (entry.getValue().primary()) {
                primaryDataSource = dataSourceBean;
            }
        }
        return primaryDataSource;
    }

    private DataSource registerDataSource(Map.Entry<String, DataSourceProperties.DataSourceGroup> dataSourceInfoEntry, DataSource dataSource) {
        String beanName = dataSourceInfoEntry.getKey() + DATA_SOURCE_SUFFIX;
        var dataSourceInfo = dataSourceInfoEntry.getValue();

        DataSource initializedDataSource = initializeAndWrapDataSource(dataSourceInfo.isCluster(), dataSource, beanName);
        registerBean(beanName, initializedDataSource, dataSourceInfo.primary());
        return initializedDataSource;
    }

    private DataSource initializeAndWrapDataSource(boolean isCluster, DataSource dataSource, String beanName) {
        if (isCluster) {
            applicationContext.getBeanFactory().initializeBean(dataSource, beanName);
            return new LazyConnectionDataSourceProxy(dataSource);
        }
        return dataSource;
    }

    private void registerBean(String beanName, Object object, boolean isPrimary) {
        var beanFactory = applicationContext.getBeanFactory();
        var beanDefinition = BeanDefinitionBuilder.genericBeanDefinition()
                .setPrimary(isPrimary)
                .getBeanDefinition();
        ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(beanName, beanDefinition);
        beanFactory.registerSingleton(beanName, object);
        beanFactory.initializeBean(object, beanName);
    }
}
