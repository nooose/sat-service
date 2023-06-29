package com.sat.auth.application;

import com.sat.auth.application.dto.OAuthAttributes;
import com.sat.member.domain.Member;
import com.sat.member.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        String userNameAttributeName = userRequest.getClientRegistration()
            .getProviderDetails()
            .getUserInfoEndpoint()
            .getUserNameAttributeName();
        Map<String, Object> attributes = delegate.loadUser(userRequest).getAttributes();
        OAuthAttributes extractedAttributes = OAuthAttributes.of(userNameAttributeName, attributes);

        Member member = memberFrom(extractedAttributes);
        return (OAuth2User) member;
    }

    private Member memberFrom(OAuthAttributes attributes) {
        long id = attributes.oAuth2UserInfo().getId();
        return memberRepository.findBySocialId(id)
            .orElseGet(() -> saveMember(id));
    }

    private Member saveMember(long id) {
        Member createdUser = new Member(id);
        return memberRepository.save(createdUser);
    }
}
