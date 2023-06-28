package com.sat.auth.oauth2.domain;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public Long getId() {
        return (Long) attributes.get("id");
    }
}
