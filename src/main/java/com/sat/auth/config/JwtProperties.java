package com.sat.auth.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigurationProperties("jwt")
public record JwtProperties (
        String secretKey,
        Access access,
        Refresh refresh
) {
    public record Access(
            Duration expirationTime,
            String header) {
    }

    public record Refresh(
            Duration expirationTime,
            String header) {
    }
}
