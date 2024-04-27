package com.sat.common.config.security

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.sat.user.domain.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser

@JsonIgnoreProperties(
    value = [
        "idToken", "userInfo", "attributes", "claims", "address",
        "subject", "expiresAt", "issuer", "audience", "issuedAt",
        "nonce", "authenticatedAt", "authorizedParty", "accessTokenHash",
        "authenticationContextClass", "authenticationMethods", "authorizationCodeHash",
        "locale", "zoneInfo", "familyName", "profile", "picture", "website", "gender",
        "fullName", "givenName", "middleName", "authorities", "nickName",
        "birthdate", "phoneNumber", "updatedAt", "preferredUsername",
        "emailVerified", "phoneNumberVerified",
    ]
)
data class AuthenticatedMember(
    val id: Long,
    private val name: String,
    val nickname: String,
    private val email: String,
    val avatar: String,
    private val idToken: OidcIdToken,
) : OidcUser {
    override fun getName(): String {
        return name
    }

    override fun getAttributes(): Map<String, Any> {
        return mapOf()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return AuthorityUtils.NO_AUTHORITIES
    }

    override fun getClaims(): Map<String, Any> {
        return idToken.claims
    }

    override fun getUserInfo(): OidcUserInfo {
        return OidcUserInfo(claims)
    }

    override fun getIdToken(): OidcIdToken {
        return idToken
    }

    override fun getEmail(): String {
        return avatar
    }

    companion object {
        fun from(request: OidcUserRequest, loginMember: Member): AuthenticatedMember {
            return AuthenticatedMember(
                id = loginMember.id!!,
                name = request.idToken.nickName as String,
                nickname = request.idToken.nickName as String,
                email = request.idToken.email as String,
                avatar = request.idToken.claims["picture"] as String,
                request.idToken
            )
        }
    }
}
