package com.sat.auth.config.jwt;

import com.sat.auth.domain.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    private final JwtProcessor jwtProcessor;

    public Authentication authenticate(String token) {
        if (!jwtProcessor.isValidToken(token)) {
            return JwtAuthenticationToken.unauthenticatedToken();
        }

        return JwtAuthenticationToken.authenticatedToken(jwtProcessor.getId(token), jwtProcessor.getAuthorities(token));
    }
}
