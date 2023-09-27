package com.sat.auth.config.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.auth.config.dto.TokenRequest;
import com.sat.auth.config.jwt.TokenPair;
import com.sat.auth.domain.JwtAuthenticationToken;
import com.sat.auth.domain.RefreshAuthenticationToken;
import com.sat.common.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final int PROVIDER_INDEX = 1;

    private final ObjectMapper objectMapper;
    private final TokenRepository tokenRepository;

    public JwtLoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, TokenRepository tokenRepository) {
        super("/*/token", authenticationManager);
        this.objectMapper = objectMapper;
        this.tokenRepository = tokenRepository;
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

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(objectMapper.writeValueAsString(tokenPair));
        } catch (IOException e) {
            throw new IllegalArgumentException(TokenPair.class.getSimpleName() + " JSON 직렬화 실패");
        }

        tokenRepository.save(tokenPair);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.of(failed)));
        } catch (IOException e) {
            throw new IllegalArgumentException(TokenPair.class.getSimpleName() + " JSON 직렬화 실패");
        }
    }
}
