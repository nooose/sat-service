package com.sat.auth.application.dto;


public record TokenPair(
        String accessToken,
        String refreshToken
) {
}
