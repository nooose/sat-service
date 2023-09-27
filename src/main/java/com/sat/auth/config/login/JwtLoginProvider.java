package com.sat.auth.config.login;

import com.sat.auth.config.login.oauth2.UserInfoClient;
import com.sat.auth.config.dto.OAuth2Response;
import com.sat.auth.config.application.JwtProcessor;
import com.sat.auth.config.application.dto.TokenPair;
import com.sat.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtLoginProvider implements AuthenticationProvider {

    private final List<UserInfoClient> userInfoClients;
    private final JwtProcessor jwtProcessor;
    private final MemberService memberService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String accessToken = (String) authentication.getCredentials();
        String providerName = "kakao";
        OAuth2Response oAuth2Response = userInfoClients.stream()
                .filter(it -> it.isSupported(providerName))
                .findAny()
                .map(it -> it.response(accessToken))
                .orElseThrow(() -> new ProviderNotFoundException(providerName + "은(는) 지원하지 않습니다."));

        memberService.joinIfNotExists(oAuth2Response.id(), oAuth2Response.name());
        // TODO: Member 권한까지 DB에서 조회하기
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(RoleType.MEMBER.getName()));
        TokenPair tokenPair = jwtProcessor.createToken(oAuth2Response.id(), authorities);
        return JwtAuthenticationToken.authenticatedToken(oAuth2Response.id(), tokenPair, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
