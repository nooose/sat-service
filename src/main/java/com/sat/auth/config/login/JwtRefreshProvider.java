package com.sat.auth.config.login;

import com.sat.auth.config.jwt.JwtProcessor;
import com.sat.auth.config.jwt.TokenPair;
import com.sat.auth.domain.JwtAuthenticationToken;
import com.sat.auth.domain.RefreshAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtRefreshProvider implements AuthenticationProvider {

    private final JwtProcessor jwtProcessor;
    private final TokenRepository tokenRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String refreshToken = (String) authentication.getCredentials();
        String expiredAccessToken = tokenRepository.findAccessTokenByRefreshToken(refreshToken)
            .orElseThrow(() -> new BadCredentialsException("갱신 정보가 올바르지 않습니다."));

        String id = jwtProcessor.getId(expiredAccessToken);
        var roles = jwtProcessor.getAuthorities(expiredAccessToken);
        TokenPair tokenPair = jwtProcessor.createToken(id, roles);
        return JwtAuthenticationToken.authenticatedToken(id, tokenPair, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
