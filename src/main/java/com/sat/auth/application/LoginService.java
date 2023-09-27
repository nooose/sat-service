package com.sat.auth.application;

import com.sat.auth.config.dto.OAuth2Response;
import com.sat.auth.config.dto.TokenRequest;
import com.sat.auth.config.jwt.JwtProcessor;
import com.sat.auth.config.jwt.TokenPair;
import com.sat.auth.domain.RoleType;
import com.sat.common.exception.NotSupportedException;
import com.sat.member.application.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class LoginService {

    private final List<UserInfoClient> userInfoClients;
    private final MemberService memberService;
    private final JwtProcessor jwtProcessor;

    @Transactional
    public TokenPair login(TokenRequest tokenRequest, String providerName) {
        OAuth2Response oAuth2Response = userInfoClients.stream()
            .filter(it -> it.isSupported(providerName))
            .findAny()
            .map(it -> it.response(tokenRequest.accessToken()))
            .orElseThrow(() -> new NotSupportedException(providerName + "은(는) 지원하지 않습니다."));
        memberService.joinIfNotExists(oAuth2Response.id(), oAuth2Response.name());
        return jwtProcessor.createToken(oAuth2Response.id(), List.of(new SimpleGrantedAuthority(RoleType.MEMBER.getName())));
    }
}
