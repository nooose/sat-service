package com.sat.common.config.security

import com.sat.user.application.MemberLoginService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration(proxyBeanMethods = false)
class SecurityConfig {

    @Bean
    fun filterChain(
            http: HttpSecurity,
            corsConfigurationSource: UrlBasedCorsConfigurationSource,
            oidcService: OAuth2UserService<OidcUserRequest, OidcUser>,
            clientRegistrationRepository: ClientRegistrationRepository,
            oidcSuccessHandler: OidcSuccessHandler,
            entryPoint: CustomAuthenticationEntryPoint,
    ): SecurityFilterChain {
        val resolver = DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI)
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce())

        http {
            csrf { disable() }
            cors { configurationSource = corsConfigurationSource }
            formLogin { disable() }
            headers { frameOptions { sameOrigin = true } }
            oauth2Login {
                authorizationEndpoint { authorizationRequestResolver = resolver }
                userInfoEndpoint { oidcUserService = oidcService }
                authenticationSuccessHandler = oidcSuccessHandler
            }
            authorizeHttpRequests {
                authorize(HttpMethod.GET, "/board/articles", permitAll)
                authorize("/**", authenticated)
            }
            exceptionHandling {
                authenticationEntryPoint = entryPoint
            }
        }
        return http.build()
    }

    @Bean
    fun oAuth2UserService(memberLoginService: MemberLoginService): OAuth2UserService<OidcUserRequest, OidcUser> {
        return OAuth2UserService {
            val name = it.idToken.nickName as String
            val email = it.idToken.email as String
            val loginMember = memberLoginService.login(name, email)
            AuthenticatedMember.from(it, loginMember)
        }
    }
}
