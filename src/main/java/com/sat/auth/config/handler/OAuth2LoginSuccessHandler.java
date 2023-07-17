package com.sat.auth.config.handler;

import com.sat.auth.application.MemberAccountReadService;
import com.sat.auth.application.MemberAccountWriteService;
import com.sat.auth.config.jwt.JwtProcessor;
import com.sat.auth.config.jwt.TokenPair;
import com.sat.auth.domain.OAuth2MemberPrincipal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.sat.auth.config.jwt.JwtProcessor.ACCESS_TOKEN;
import static com.sat.auth.config.jwt.JwtProcessor.REFRESH_TOKEN;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProcessor jwtProcessor;
    private final MemberAccountWriteService memberWriteService;
    private final MemberAccountReadService memberReadService;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2MemberPrincipal principal = (OAuth2MemberPrincipal) authentication.getPrincipal();

        if (!memberReadService.existById(principal.id())) {
            memberWriteService.join(principal.id(), principal.nickname());
        }

        TokenPair tokenPair = jwtProcessor.createToken(principal.id());
        saveToken(tokenPair, response);
        response.sendRedirect("/");
    }

    private void saveToken(TokenPair tokenPair, HttpServletResponse response) {
        Cookie accessTokenCookie = createCookie(ACCESS_TOKEN, tokenPair.accessToken());
        Cookie refreshTokenCookie = createCookie(REFRESH_TOKEN, tokenPair.refreshToken());
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        // TODO: 쿠키 만료시간을 토큰과 동일한 시간으로 조정
        cookie.setMaxAge(60 * 60);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
