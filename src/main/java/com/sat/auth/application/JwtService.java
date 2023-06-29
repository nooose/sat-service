package com.sat.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sat.auth.config.JwtProperties;
import com.sat.member.infrastructure.repository.MemberRepository;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@Service
public class JwtService {
    public static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    public static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

    private final MemberRepository memberRepository;
    private final JwtProperties jwtProperties;

    public String createAccessToken(Long id) {
        Date expiresAt = calculateDate(LocalDateTime.now(), jwtProperties.access().expirationTime());
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(expiresAt)
                .withClaim("id", id)
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    public String createRefreshToken() {
        Date expiresAt = calculateDate(LocalDateTime.now(), jwtProperties.refresh().expirationTime());
        return JWT.create()
            .withSubject(REFRESH_TOKEN_SUBJECT)
            .withExpiresAt(expiresAt)
            .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    private Date calculateDate(LocalDateTime now, Duration duration) {
        ZonedDateTime zonedDateTime = now.plus(duration).atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public boolean isValidToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(jwtProperties.secretKey()))
                .build()
                .verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
