package com.sat.common.security

import com.sat.common.security.TestAuthUtils.authentication
import com.sat.security.AuthenticatedMember
import com.sat.user.command.domain.member.Member
import com.sat.user.command.domain.member.Role
import com.sat.user.command.domain.member.RoleType
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
        val authentication = authentication(annotation.id, annotation.name, annotation.nickname, annotation.email)
        context.authentication = authentication
        return context
    }
}

object TestAuthUtils {
    fun setAuthentication(
        id: Long = 1L,
        name: String = "테스트",
        nickName: String = "테스트",
        email: String = "test@test.com"
    ) {
        SecurityContextHolder.getContext().authentication = authentication(id, name, nickName, email)
    }

    fun authentication(
        id: Long = 1L,
        name: String = "테스트",
        nickName: String = "테스트",
        email: String = "test@test.com"
    ): OAuth2AuthenticationToken {
        val claims = mapOf(
            "id" to id,
            "name" to name,
            "nickname" to nickName,
            "email" to email,
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

        val member = Member(name, name, "test@test.com", Role(RoleType.ADMIN), id)
        val principal = AuthenticatedMember.from(oidcUserRequest, member)
        return OAuth2AuthenticationToken(principal, principal.authorities, registrationId)
    }
}

