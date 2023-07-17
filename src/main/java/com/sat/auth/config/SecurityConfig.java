package com.sat.auth.config;

import com.sat.auth.application.MemberAccountReadService;
import com.sat.auth.application.MemberAccountWriteService;
import com.sat.auth.application.dto.KakaoOAuth2Response;
import com.sat.auth.domain.MemberPrincipal;
import com.sat.auth.config.handler.CustomAuthenticationEntrypoint;
import com.sat.auth.config.handler.OAuth2LoginFailureHandler;
import com.sat.auth.config.handler.OAuth2LoginSuccessHandler;
import com.sat.auth.config.jwt.JwtAuthenticationProvider;
import com.sat.auth.config.jwt.JwtFilter;
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

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService,
            JwtAuthenticationProvider provider
    ) throws Exception {
        RequestMatcher defaultRequestMatcher = defaultRequestMatcher();
        return http.formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(defaultRequestMatcher).permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oAuth -> oAuth
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(oAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler))
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .addFilterBefore(new JwtFilter(defaultRequestMatcher, provider), OAuth2AuthorizationRequestRedirectFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new CustomAuthenticationEntrypoint()))
                .build();
    }

    private RequestMatcher defaultRequestMatcher() {
        return RequestMatchers.anyOf(
            PathRequest.toH2Console(),
            AntPathRequestMatcher.antMatcher(GET, "/"),
            AntPathRequestMatcher.antMatcher(GET, "/oauth2/authorization/**"),
            AntPathRequestMatcher.antMatcher(GET, "/login/oauth2/code/**")
        );
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(
            MemberAccountWriteService memberWriteService,
            MemberAccountReadService memberReadService
    ) {
        final var delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            var attributes = oAuth2User.getAttributes();

            KakaoOAuth2Response userInfo = KakaoOAuth2Response.of(attributes);
            if (!memberReadService.existById(userInfo.id())) {
                memberWriteService.join(userInfo.id(), userInfo.kakaoAccount().profile().nickname());
            }
            return MemberPrincipal.of(userInfo.id(), attributes);
        };
    }
}
