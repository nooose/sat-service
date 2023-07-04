package com.sat.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sat.auth.application.dto.Token;
import com.sat.auth.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtProvider {
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";

    private final JwtProperties jwtProperties;

    public Token createToken(String id) {
        String accessToken = createToken(ACCESS_TOKEN, id, jwtProperties.access().expirationTime());
        String refreshToken = createToken(REFRESH_TOKEN, null, jwtProperties.refresh().expirationTime());

        return new Token(accessToken, refreshToken);
    }

    private String createToken(String subject, String id, Duration duration) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + duration.toMillis());

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expiredDate)
                .withClaim("id", id)
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    public boolean isValidToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(jwtProperties.secretKey()))
                .build()
                .verify(token);
        } catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
