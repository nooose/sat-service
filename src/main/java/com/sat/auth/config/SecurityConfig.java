package com.sat.auth.config;

import com.sat.auth.config.dto.KakaoOAuth2Response;
import com.sat.auth.config.handler.CustomAuthenticationEntrypoint;
import com.sat.auth.config.handler.OAuth2LoginFailureHandler;
import com.sat.auth.config.handler.OAuth2LoginSuccessHandler;
import com.sat.auth.config.jwt.JwtAuthenticationFilter;
import com.sat.auth.config.jwt.JwtAuthenticationProvider;
import com.sat.auth.domain.OAuth2MemberPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatchers;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private static final RequestMatcher ACCESSIBLE_REQUESTS;

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    static {
        ACCESSIBLE_REQUESTS = RequestMatchers.anyOf(
                PathRequest.toH2Console(),
                AntPathRequestMatcher.antMatcher(GET, "/"),
                AntPathRequestMatcher.antMatcher(GET, "/oauth2/authorization/**"),
                AntPathRequestMatcher.antMatcher(GET, "/login/oauth2/code/**")
        );
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var jwtAuthFilter = new JwtAuthenticationFilter(ACCESSIBLE_REQUESTS, jwtAuthenticationProvider);
        return http.formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ACCESSIBLE_REQUESTS).permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oAuth -> oAuth
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(oAuth2UserService()))
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler))
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .addFilterBefore(jwtAuthFilter, OAuth2AuthorizationRequestRedirectFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new CustomAuthenticationEntrypoint()))
                .build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(
    ) {
        final var delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            var attributes = oAuth2User.getAttributes();
            KakaoOAuth2Response oAuth2UserResponse = KakaoOAuth2Response.of(attributes);
            return OAuth2MemberPrincipal.of(oAuth2UserResponse.id(), oAuth2UserResponse.kakaoAccount().profile().nickname(), attributes);
        };
    }
}
