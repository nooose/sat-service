package com.sat.auth.config;

import com.sat.auth.config.jwt.JwtAuthenticationFilter;
import com.sat.auth.config.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private static final RequestMatcher ALLOWED_REQUEST_MATCHER;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    static {
        ALLOWED_REQUEST_MATCHER = RequestMatchers.anyOf(
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/oauth2"),
                AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/login/kakao"),
                AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/v1/studygroups"),
                PathRequest.toH2Console()
        );
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("index.html", "manifest.json", "/static/**", "/images/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var jwtAuthFilter = new JwtAuthenticationFilter(ALLOWED_REQUEST_MATCHER, jwtAuthenticationProvider);
        return http.csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ALLOWED_REQUEST_MATCHER).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
