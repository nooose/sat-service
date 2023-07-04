package com.sat.auth.config.handler;

import com.sat.auth.application.JwtProvider;
import com.sat.auth.application.dto.Token;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.sat.auth.application.JwtProvider.ACCESS_TOKEN;
import static com.sat.auth.application.JwtProvider.REFRESH_TOKEN;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Token token = jwtProvider.createToken(authentication.getName());
        saveToken(token, response);
    }

    private void saveToken(Token token, HttpServletResponse response) {
        Cookie accessTokenCookie = createCookie(ACCESS_TOKEN, token.accessToken());
        Cookie refreshTokenCookie = createCookie(REFRESH_TOKEN, token.refreshToken());
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
