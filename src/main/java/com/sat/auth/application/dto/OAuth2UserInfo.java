package com.sat.auth.application.dto;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract Long getId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "socialId", 네이버 - "socialId"

}
