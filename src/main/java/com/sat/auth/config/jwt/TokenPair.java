package com.sat.auth.config.jwt;


public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
