package com.sat.auth.config.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.auth.application.dto.TokenPair;
import com.sat.auth.config.dto.TokenRequest;
import com.sat.auth.domain.RefreshToken;
import com.sat.auth.domain.RefreshTokenRepository;
import com.sat.common.dto.ErrorResponse;
import com.sat.common.util.ResponseUtils;
import com.sat.member.domain.MemberId;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final int PROVIDER_INDEX = 1;

    private final RefreshTokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    public JwtLoginFilter(AuthenticationManager authenticationManager, RefreshTokenRepository tokenRepository, ObjectMapper objectMapper) {
        super("/*/token", authenticationManager);
        this.tokenRepository = tokenRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher.antMatcher(HttpMethod.POST, filterProcessesUrl));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String providerName = request.getRequestURI().split("/")[PROVIDER_INDEX];
        TokenRequest tokenRequest = objectMapper.readValue(request.getInputStream(), TokenRequest.class);
        Authentication authentication = createAuthenticationFrom(tokenRequest, providerName);
        return getAuthenticationManager().authenticate(authentication);
    }

    private Authentication createAuthenticationFrom(TokenRequest tokenRequest, String providerName) {
        if (tokenRequest.isRefreshRequest()) {
            return RefreshAuthenticationToken.from(tokenRequest.refreshToken());
        }

        return JwtAuthenticationToken.InsecureToken(tokenRequest.accessToken(), providerName);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        var authenticationToken = (JwtAuthenticationToken) authResult;
        TokenPair tokenPair = authenticationToken.getTokenPair();
        ResponseUtils.setResponse(response, HttpStatus.OK, tokenPair);
        tokenRepository.save(new RefreshToken(tokenPair.refreshToken(), MemberId.of(authenticationToken.getPrincipal())));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        ResponseUtils.setResponse(response, HttpStatus.UNAUTHORIZED, ErrorResponse.of(failed));
    }
}
