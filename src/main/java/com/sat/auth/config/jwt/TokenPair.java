package com.sat.auth.config.jwt;

import com.sat.auth.domain.AuthToken;

public record TokenPair(
        String accessToken,
        String refreshToken
) {
    public static TokenPair of(AuthToken entity) {
        return new TokenPair(entity.getAccessToken(), entity.getRefreshToken());
    }
}
