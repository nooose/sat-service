package com.sat.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties("jwt")
public class JwtProperties {

    private final String secretKey;
    private final Access access;
    private final Refresh refresh;

    public JwtProperties(String secretKey, Access access, Refresh refresh) {
        this.secretKey = secretKey;
        this.access = access;
        this.refresh = refresh;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Access {
        private final long expiration;
        private final String header;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Refresh {
        private final long expiration;
        private final String header;
    }
}
