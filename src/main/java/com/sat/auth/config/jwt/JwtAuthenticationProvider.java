package com.sat.auth.config.jwt;

import com.sat.auth.domain.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider {

    private final JwtProcessor jwtProcessor;

    public void authenticate(String token) {
        if (!jwtProcessor.isValidToken(token)) {
            return;
        }

        JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(jwtProcessor.getId(token), jwtProcessor.getRoles(token));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
