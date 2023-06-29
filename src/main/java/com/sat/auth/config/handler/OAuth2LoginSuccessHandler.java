package com.sat.auth.config.handler;

import com.sat.auth.application.JwtService;
import com.sat.member.domain.Member;
import com.sat.member.domain.MemberId;
import com.sat.member.infrastructure.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String memberId = memberRepository.findById(new MemberId(token.getName()))
            .map(Member::getId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));

        String accessToken = jwtService.createAccessToken(memberId);
        String refreshToken = jwtService.createRefreshToken();
        saveToken("accessToken", accessToken, response);
        saveToken("refreshToken", refreshToken, response);
    }

    private void saveToken(String cookieName, String token, HttpServletResponse response) {
        Cookie cookie = createCookie(cookieName, token);
        response.addCookie(cookie);
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
