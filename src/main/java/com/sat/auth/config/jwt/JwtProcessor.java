package com.sat.auth.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProcessor {
    public static final String ACCESS_TOKEN = "sat-access-token";
    public static final String REFRESH_TOKEN = "sat-refresh-token";

    private final JwtProperties jwtProperties;

    public TokenPair createToken(String id, Collection<? extends GrantedAuthority> authorities) {
        String accessToken = createToken(ACCESS_TOKEN, id, authorities, jwtProperties.access().expirationTime());
        String refreshToken = createToken(REFRESH_TOKEN, null, authorities, jwtProperties.refresh().expirationTime());

        return new TokenPair(accessToken, refreshToken);
    }

    private String createToken(String subject, String id, Collection<? extends GrantedAuthority> authorities, Duration duration) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + duration.toMillis());

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(expiredDate)
                .withClaim("id", id)
                .withClaim("role", authorities.stream().map(GrantedAuthority::getAuthority).toList())
                .sign(Algorithm.HMAC512(jwtProperties.secretKey()));
    }

    public String getId(String token) {
        return JWT.decode(token)
                .getClaim("id")
                .asString();
    }

    public List<? extends GrantedAuthority> getAuthorities(String token) {
        return JWT.decode(token)
                .getClaim("role")
                .asList(String.class)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
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
