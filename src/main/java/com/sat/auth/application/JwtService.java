package com.sat.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sat.auth.config.JwtProperties;
import com.sat.member.infrastructure.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Getter
@RequiredArgsConstructor
@Service
public class JwtService {

    private final MemberRepository memberRepository;
    private final JwtProperties jwtProperties;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String BEARER = "Bearer";

    /**
     * AccessToken 생성 메서드
     * 따로 넣을 커스텀 정보(claim)
     * - 카카오에서 받아온 소셜아이디
     */
    public String createAccessToken(Long socialId) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + jwtProperties.access().expirationTime().toMillis()))
                .withClaim("socialId", socialId)
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    /*
     * RefreshToken 생성
     * 따로 넣을 커스텀 정보(claim)가 없으므로 withClaim 생략
     * */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + jwtProperties.refresh().expirationTime().toMillis()))
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    public void saveAccessAndRefreshTokenAtCookie(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie accessTokenCookie = new Cookie("AccessToken", accessToken);
        Cookie refreshTokenCookie = new Cookie("RefreshToken", refreshToken);

        accessTokenCookie.setMaxAge(60 * 60);
        refreshTokenCookie.setMaxAge(60 * 60);

        log.info("accessToken = {}", accessTokenCookie.getValue());
        log.info("refreshToken = {}", refreshTokenCookie.getValue());

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        log.info("Access Token, Refresh Token 쿠키 설정 완료");
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(jwtProperties.secretKey())).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    // 만료 시간 검증
    public void validExpiredTime(String token) {
        JWT.require(Algorithm.HMAC512(jwtProperties.secretKey())).build().verify(token).getExpiresAt();
    }
}
