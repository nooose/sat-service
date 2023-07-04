package com.sat.auth.application.dto;

import java.util.Map;

public record KakaoOAuth2Response(
        String id,
        Map<String, Object> properties,
        Profile profile
) {
    public record Profile(
            String nickname
    ) {

        public static Profile of(Map<String, Object> attributes) {
            return new Profile(String.valueOf(attributes.get("nickname")));
        }
    }


    public static KakaoOAuth2Response of(Map<String, Object> attributes) {
        return new KakaoOAuth2Response(
                String.valueOf(attributes.get("id")),
                (Map<String, Object>) attributes.get("properties"),
                Profile.of((Map<String, Object>) attributes.get("profile")));
    }
}
