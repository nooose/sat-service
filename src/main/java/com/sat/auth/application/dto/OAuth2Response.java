package com.sat.auth.application.dto;

import java.util.Map;
import lombok.Getter;

public abstract class OAuth2Response {
    @Getter
    protected Map<String, Object> attributes;

    OAuth2Response(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public static OAuth2Response of(Map<String, Object> attributes) {
        return new KakaoOAuth2Response(attributes);
    }

    public abstract String providerId();
}
