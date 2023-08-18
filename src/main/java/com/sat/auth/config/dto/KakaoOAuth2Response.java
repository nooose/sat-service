package com.sat.auth.config.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public record KakaoOAuth2Response(
        String id,
        Map<String, Object> properties,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            Profile profile
    ) {
        public record Profile(
                String nickname
        ) {
        }
    }

    @Override
    public String id() {
        return "kakao-" + id;
    }
}
