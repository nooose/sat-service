package com.sat.auth.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final BearerTokenizer bearerTokenizer;
    private final RequestMatcher requestMatcher;
    private final JwtAuthenticationProvider provider;

    public JwtAuthenticationFilter(RequestMatcher requestMatcher, JwtAuthenticationProvider provider) {
        this.bearerTokenizer = new BearerTokenizer();
        this.requestMatcher = requestMatcher;
        this.provider = provider;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwt = bearerTokenizer.token(request);

        if (jwt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        provider.authenticate(jwt.get());
        filterChain.doFilter(request, response);
    }
}
