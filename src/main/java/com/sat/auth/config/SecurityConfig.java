package com.sat.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sat.auth.config.exception.CustomAccessDeniedHandler;
import com.sat.auth.config.exception.CustomAuthenticationEntryPoint;
import com.sat.auth.config.login.JwtAuthenticationFilter;
import com.sat.auth.config.login.JwtAuthenticationProvider;
import com.sat.auth.config.login.JwtLoginFilter;
import com.sat.auth.domain.RefreshTokenRepository;
import com.sat.member.domain.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    @Value("${spring.web.cors.allowed-origins}")
    private List<String> allowedOrigins;

    private static final RequestMatcher ALLOWED_REQUEST_MATCHER;

    static {
        ALLOWED_REQUEST_MATCHER = RequestMatchers.anyOf(
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/v1/studygroups"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/error"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/docs/*"),
                PathRequest.toH2Console()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CorsConfigurationSource corsConfigurationSource,
                                                   AuthenticationManager authenticationManager,
                                                   RefreshTokenRepository tokenRepository,
                                                   JwtAuthenticationProvider jwtAuthenticationProvider,
                                                   ObjectMapper objectMapper) throws Exception {
        var jwtLoginFilter = new JwtLoginFilter(authenticationManager, tokenRepository, objectMapper);
        var jwtAuthFilter = new JwtAuthenticationFilter(ALLOWED_REQUEST_MATCHER, jwtAuthenticationProvider);
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ALLOWED_REQUEST_MATCHER).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, JwtLoginFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.setAllowedOrigins(allowedOrigins);
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD"));
        corsConfig.setAllowedHeaders(List.of("*"));
        corsConfig.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();
        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
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
