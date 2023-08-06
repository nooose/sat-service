package com.sat.auth.config.handler;

import com.sat.auth.config.jwt.JwtProcessor;
import com.sat.auth.config.jwt.TokenPair;
import com.sat.auth.domain.AuthToken;
import com.sat.auth.domain.AuthTokenRepository;
import com.sat.auth.domain.OAuth2MemberPrincipal;
import com.sat.member.application.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProcessor jwtProcessor;
    private final MemberService memberService;
    private final AuthTokenRepository tokenRepository;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2MemberPrincipal principal = (OAuth2MemberPrincipal) authentication.getPrincipal();

        memberService.joinIfNotExists(principal.id(), principal.nickname());

        TokenPair tokenPair = jwtProcessor.createToken(principal.id());
        AuthToken authToken = new AuthToken(tokenPair.accessToken(), tokenPair.refreshToken());
        tokenRepository.save(authToken);
        response.sendRedirect("/login-success?code=" + authToken.getId());
    }
}
