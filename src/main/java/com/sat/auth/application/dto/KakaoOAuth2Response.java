package com.sat.auth.application.dto;

import java.util.Map;

public record KakaoOAuth2Response(
        String id,
        Map<String, Object> properties,
        KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            Profile profile
    ) {
        public record Profile(
                String nickname
        ) {
            public static Profile of(Map<String, Object> attributes) {
                return new Profile(String.valueOf(attributes.get("nickname")));
            }
        }

        public static KakaoAccount of(Map<String, Object> attributes) {
            return new KakaoAccount(Profile.of((Map<String, Object>) attributes.get("profile")));
        }
    }

    public static KakaoOAuth2Response of(Map<String, Object> attributes) {
        return new KakaoOAuth2Response(
                String.valueOf(attributes.get("id")),
                (Map<String, Object>) attributes.get("properties"),
                KakaoAccount.of((Map<String, Object>) attributes.get("kakao_account"))
        );
    }
}
