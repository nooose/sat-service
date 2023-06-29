package com.sat.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sat.auth.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public String createAccessToken(String id) {
        Date expiresAt = calculateDate(LocalDateTime.now(), jwtProperties.access().expirationTime());
        return JWT.create()
            .withSubject("accessToken")
            .withExpiresAt(expiresAt)
            .withClaim("id", id)
            .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    public String createRefreshToken() {
        Date expiresAt = calculateDate(LocalDateTime.now(), jwtProperties.refresh().expirationTime());
        return JWT.create()
            .withSubject("refreshToken")
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
                .acceptExpiresAt(jwtProperties.access().expirationTime().toSeconds())
                .build()
                .verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
