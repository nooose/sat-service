package com.sat.auth.config.login;

import com.sat.auth.application.JwtProcessor;
import com.sat.auth.application.dto.TokenPair;
import com.sat.auth.domain.Token;
import com.sat.auth.domain.TokenRepository;
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
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BadCredentialsException("갱신 정보가 올바르지 않습니다."));
        String expiredAccessToken = token.getAccessToken();
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
