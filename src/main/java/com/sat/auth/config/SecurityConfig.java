package com.sat.auth.config;

import com.sat.auth.application.MemberAccountService;
import com.sat.auth.application.dto.MemberPrincipal;
import com.sat.auth.application.dto.OAuth2Response;
import com.sat.auth.config.handler.OAuth2LoginFailureHandler;
import com.sat.auth.config.handler.OAuth2LoginSuccessHandler;
import com.sat.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService
    ) throws Exception {
        return http.formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oAuth -> oAuth
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(oAuth2UserService)))
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(MemberAccountService accountService) {
        final var delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            var attributes = oAuth2User.getAttributes();

            OAuth2Response userInfo = OAuth2Response.of(attributes);
            Member member = accountService.findOrCreate(userInfo.providerId());
            return MemberPrincipal.of(member.getId(), userInfo);
        };
    }
}
