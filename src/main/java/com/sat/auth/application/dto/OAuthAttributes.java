package com.sat.auth.application.dto;

import java.util.Map;

public record OAuthAttributes(
    String nameAttributeKey,
    OAuth2UserInfo oAuth2UserInfo
) {

    /**
     * 소셜별 정적 팩터리 메서드(ofGoogle, ofKakao, ofNaver)들은 (현재는 Kakao만 사용중)<br>
     * 각각 소셜 로그인 API에서 제공하는 회원의 식별값(socialId), attributes, nameAttributeKey를 생성
     * @param userNameAttributeName OAuth2 로그인 시 키(PK)가 되는 값
     * @param attributes OAuth 서비스의 유저 정보들
     * @return 현재는 카카오 하나지만 향후 다른 플랫폼이 추가될 시 SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
     */
    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return new OAuthAttributes(userNameAttributeName, new KakaoOAuth2UserInfo(attributes));
    }
}
