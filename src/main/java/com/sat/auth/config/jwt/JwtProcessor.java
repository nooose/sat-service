package com.sat.auth.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sat.auth.domain.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class JwtProcessor {
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";

    private final JwtProperties jwtProperties;

    public TokenPair createToken(String id) {
        String accessToken = createToken(ACCESS_TOKEN, id, jwtProperties.access().expirationTime());
        String refreshToken = createToken(REFRESH_TOKEN, null, jwtProperties.refresh().expirationTime());

        return new TokenPair(accessToken, refreshToken);
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

    public String getId(String token) {
        return decodedJWT(token)
            .getClaim("id")
            .asString();
    }

    // TODO: JWT에 권한 넣기
    public List<? extends GrantedAuthority> getRoles(String token) {
        return Set.of(RoleType.MEMBER).stream()
            .map(RoleType::getName)
            .map(SimpleGrantedAuthority::new)
            .toList();
    }

    public boolean isValidToken(String token) {
        try {
            decodedJWT(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    private DecodedJWT decodedJWT(String token) {
        return JWT.require(Algorithm.HMAC512(jwtProperties.secretKey()))
            .build()
            .verify(token);
    }
}
