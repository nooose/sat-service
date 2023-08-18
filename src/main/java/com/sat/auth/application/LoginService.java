package com.sat.auth.application;

import com.sat.auth.config.dto.OAuth2Response;
import com.sat.auth.config.dto.TokenRequest;
import com.sat.auth.config.jwt.JwtProcessor;
import com.sat.auth.config.jwt.TokenPair;
import com.sat.member.application.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final List<UserInfoClient> clients;
    private final MemberService memberService;
    private final JwtProcessor jwtProcessor;

    @Transactional
    public TokenPair login(TokenRequest tokenRequest, String providerName) {
        OAuth2Response oAuth2Response = clients.stream()
            .filter(it -> it.isSupported(providerName))
            .findAny()
            .map(it -> it.response(tokenRequest.accessToken()))
            .orElseThrow();
        memberService.joinIfNotExists(oAuth2Response.id(), oAuth2Response.name());
        return jwtProcessor.createToken(oAuth2Response.id());
    }
}
