package com.sat.auth.application.dto;

import java.util.Map;

public class KakaoOAuth2Response extends OAuth2Response {

    public KakaoOAuth2Response(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String providerId() {
        return String.valueOf(attributes.get("id"));
    }
}
