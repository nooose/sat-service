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
    private final RequestMatcher allowdRequestMatcher;
    private final JwtAuthenticationProvider authenticationProvider;

    public JwtAuthenticationFilter(RequestMatcher allowdRequestMatcher, JwtAuthenticationProvider provider) {
        this.bearerTokenizer = new BearerTokenizer();
        this.allowdRequestMatcher = allowdRequestMatcher;
        this.authenticationProvider = provider;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return allowdRequestMatcher.matches(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwt = bearerTokenizer.token(request);

        if (jwt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(jwt.get());
        authenticationProvider.authenticate(jwt.get());
        filterChain.doFilter(request, response);
    }
}
