package com.sat.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.auth.config.exception.CustomAccessDeniedHandler;
import com.sat.auth.config.exception.CustomAuthenticationEntryPoint;
import com.sat.auth.config.login.JwtAuthenticationFilter;
import com.sat.auth.config.login.JwtAuthenticationProvider;
import com.sat.auth.config.login.JwtLoginFilter;
import com.sat.member.domain.RoleType;
import com.sat.auth.config.login.oauth2.AuthorizationCodeFilter;
import com.sat.auth.domain.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private static final RequestMatcher ALLOWED_REQUEST_MATCHER;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    static {
        ALLOWED_REQUEST_MATCHER = RequestMatchers.anyOf(
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/v1/studygroups"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/error"),
                PathRequest.toH2Console()
        );
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("index.html", "manifest.json", "static/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationManager authenticationManager,
                                                   TokenRepository tokenRepository,
                                                   ObjectMapper objectMapper) throws Exception {
        var jwtLoginFilter = new JwtLoginFilter(authenticationManager, tokenRepository, objectMapper);
        var jwtAuthFilter = new JwtAuthenticationFilter(ALLOWED_REQUEST_MATCHER, jwtAuthenticationProvider);
        var authorizationCodeFilter = new AuthorizationCodeFilter();
        return http.csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ALLOWED_REQUEST_MATCHER).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authorizationCodeFilter, JwtLoginFilter.class)
                .addFilterAfter(jwtAuthFilter, AuthorizationCodeFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper))
                        .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper)))
                .build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = Arrays.stream(RoleType.values()).map(RoleType::getName)
                .collect(Collectors.joining(" > "));
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, List<AuthenticationProvider> providers) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        providers.forEach(authenticationManagerBuilder::authenticationProvider);
        return authenticationManagerBuilder.build();
    }
}
