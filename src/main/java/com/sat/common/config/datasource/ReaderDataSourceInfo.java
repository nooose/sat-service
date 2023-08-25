package com.sat.common.config.datasource;

import com.zaxxer.hikari.HikariConfig;

public class ReaderDataSourceInfo extends AbstractDataSourceInfo {

    public ReaderDataSourceInfo(String url, String username, String password, HikariConfig hikari) {
        super(url, username, password, hikari);
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }
}
