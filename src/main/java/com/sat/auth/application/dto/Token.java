package com.sat.auth.application.dto;

public record Token(
        String accessToken,
        String refreshToken
) {
}
