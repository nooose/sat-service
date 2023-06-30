package com.sat.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sat.auth.application.dto.Token;
import com.sat.auth.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(new Date())
                .withExpiresAt(addDuration(duration))
                .withClaim("id", id)
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    private Date addDuration(Duration duration) {
        return Date.from(ZonedDateTime.now().plus(duration).toInstant());
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
