package com.sat.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sat.auth.config.JwtProperties;
import com.sat.member.infrastructure.repository.MemberRepository;
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
    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

    private final MemberRepository memberRepository;
    private final JwtProperties jwtProperties;

    public String createAccessToken(Long id, Long now) {
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now + jwtProperties.access().expirationTime().toMillis()))
                .withClaim("id", id)
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    public String createRefreshToken(Long now) {
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now + jwtProperties.refresh().expirationTime().toMillis()))
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    public boolean isValidToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(jwtProperties.secretKey()))
                .build()
                .verify(token);
        } catch (JWTVerificationException e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
        return true;
    }
}
