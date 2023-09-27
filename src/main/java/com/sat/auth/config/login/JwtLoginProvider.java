package com.sat.auth.config.login;

import com.sat.auth.config.login.oauth2.UserInfoClient;
import com.sat.auth.config.dto.OAuth2Response;
import com.sat.auth.application.JwtProcessor;
import com.sat.auth.application.dto.TokenPair;
import com.sat.auth.domain.MemberRole;
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
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;

        String accessToken = authenticationToken.getCredentials();
        String providerName = authenticationToken.getProviderName();
        OAuth2Response oAuth2Response = getOAuth2Response(providerName, accessToken);

        String memberId = oAuth2Response.id();
        MemberRole memberRole = memberService.joinIfNotExists(memberId, oAuth2Response.name());
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(memberRole.getRole().getName()));
        TokenPair tokenPair = jwtProcessor.createToken(memberId, authorities);
        return JwtAuthenticationToken.authenticatedToken(memberId, tokenPair, authorities);
    }

    private OAuth2Response getOAuth2Response(String providerName, String accessToken) {
        return userInfoClients.stream()
                .filter(it -> it.isSupported(providerName))
                .findAny()
                .map(it -> it.response(accessToken))
                .orElseThrow(() -> new ProviderNotFoundException(providerName + "은(는) 지원하지 않습니다."));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
