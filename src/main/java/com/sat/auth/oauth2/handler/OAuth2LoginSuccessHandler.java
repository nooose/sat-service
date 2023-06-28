package com.sat.auth.oauth2.handler;

import com.sat.auth.application.JwtService;
import com.sat.member.domain.Member;
import com.sat.member.infrastructure.repository.MemberRepository;
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
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("OAuth2 Login 성공!");
        try {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

            Member member = memberRepository.findBySocialId(
                    Long.valueOf(token.getName())).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다")
            );

            loginSuccess(response, member);
        } catch (Exception e) {
            throw e;
        }

    }

    private void loginSuccess(HttpServletResponse response, Member member) throws IOException {
        String accessToken = jwtService.createAccessToken(member.getSocialId());
        String refreshToken = jwtService.createRefreshToken();

        member.updateRefreshToken(refreshToken);
        memberRepository.save(member);

        jwtService.saveAccessAndRefreshTokenAtCookie(response, accessToken, refreshToken);

        response.sendRedirect("/logInSuccessfully?socialId=" + member.getSocialId());
    }
}
