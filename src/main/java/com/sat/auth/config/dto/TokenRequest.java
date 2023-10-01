package com.sat.auth.config.dto;

import org.springframework.util.StringUtils;

public record TokenRequest(
    TokenRequestType type,
    String accessToken,
    String refreshToken
) {
    public boolean hasRefreshRequest() {
        return type == TokenRequestType.REFRESH && StringUtils.hasText(refreshToken);
    }
}
