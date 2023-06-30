package com.sat.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sat.auth.application.dto.Token;
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

    public Token createToken(String id) {
        String accessToken = createToken("accessToken", id, jwtProperties.access().expirationTime());
        String refreshToken = createToken("refreshToken", null, jwtProperties.refresh().expirationTime());

        return new Token(accessToken, refreshToken);
    }

    private String createToken(String subject, String id, Duration duration) {
        Date expiresAt = calculateDate(LocalDateTime.now(), duration);
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(expiresAt)
                .withClaim("id", id)
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
