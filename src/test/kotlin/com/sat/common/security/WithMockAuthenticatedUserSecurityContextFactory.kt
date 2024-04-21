package com.sat.common.security

import com.sat.common.config.security.AuthenticatedMember
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockAuthenticatedUserSecurityContextFactory : WithSecurityContextFactory<WithAuthenticatedUser> {
    override fun createSecurityContext(annotation: WithAuthenticatedUser): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()

        val claims = mapOf(
            "id" to annotation.id,
            "name" to annotation.name,
            "nickname" to annotation.nickname,
            "email" to annotation.email,
            "picture" to "",
        )
        val accessToken = OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", null, null)
        val oidcIdToken = OidcIdToken("token", null, null, claims)
        val registrationId = "test"
        val clientRegistration = ClientRegistration.withRegistrationId(registrationId)
            .clientId(registrationId)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("https://")
            .authorizationUri("https://")
            .tokenUri("https://")
            .issuerUri("https://")
            .build()
        val oidcUserRequest = OidcUserRequest(clientRegistration, accessToken, oidcIdToken)
        val principal = AuthenticatedMember.from(oidcUserRequest)
        val authentication: Authentication = OAuth2AuthenticationToken(principal.copy(annotation.id), principal.authorities, registrationId)

        context.authentication = authentication
        return context
    }
}
