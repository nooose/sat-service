package com.sat.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("jwt")
public record JwtProperties (
        String secretKey,
        TokenConfig access,
        TokenConfig refresh
) {
    public record TokenConfig(
            Duration expirationTime) {
    }
}
