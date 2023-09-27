package com.sat.auth.config.application.dto;


public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
