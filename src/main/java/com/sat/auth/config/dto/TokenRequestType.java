package com.sat.auth.config.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TokenRequestType {
    TOKEN, REFRESH;

    @JsonCreator
    public static TokenRequestType of(String symbol) {
        return TokenRequestType.valueOf(symbol.toUpperCase());
    }
}
