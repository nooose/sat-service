package com.sat.auth.oauth2.domain.request;


import com.sat.auth.oauth2.domain.KakaoOAuth2UserInfo;
import com.sat.auth.oauth2.domain.OAuth2UserInfo;
import com.sat.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    /**
     * 현재는 카카오 하나지만 향후 다른 플랫폼이 추가될 시 SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
     * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
     * 소셜별 of 메소드(ofGoogle, ofKaKao, ofNaver)들은 (현재는 KaKao만 사용중)
     * 각각 소셜 로그인 API에서 제공하는 회원의 식별값(socialId), attributes, nameAttributeKey를 저장 후 build
     */
    public static OAuthAttributes of(String userNameAttributeName, Map<String, Object> attributes) {
            return ofKakao(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public Member toEntity(OAuth2UserInfo oauth2UserInfo) {
        return new Member(oauth2UserInfo.getId());
    }
}
