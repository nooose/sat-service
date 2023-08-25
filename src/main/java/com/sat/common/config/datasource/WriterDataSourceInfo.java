package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;

public class WriterDataSourceInfo extends AbstractDataSourceInfo {

    public WriterDataSourceInfo(String url, String username, String password, HikariConfig hikari) {
        super(url, username, password, hikari);
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }
}
