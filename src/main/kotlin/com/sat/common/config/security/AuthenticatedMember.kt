package com.sat.common.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser

data class AuthenticatedMember(
    val id: Long,
    private val name: String,
    private val authorities: Collection<GrantedAuthority>,
    private val claims: Map<String, Any>,
    private val idToken: OidcIdToken,
) : OidcUser {
    override fun getName(): String {
        return name
    }

    override fun getAttributes(): Map<String, Any> {
        return mapOf()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getClaims(): Map<String, Any> {
        return claims
    }

    override fun getUserInfo(): OidcUserInfo {
        return OidcUserInfo(claims)
    }

    override fun getIdToken(): OidcIdToken {
        return idToken
    }

    override fun getEmail(): String {
        return claims["email"] as String
    }

    companion object {
        fun from(request: OidcUserRequest): AuthenticatedMember {
            val authenticatedMember = AuthenticatedMember(
                id = 0L,
                name = request.idToken.nickName as String,
                authorities = AuthorityUtils.NO_AUTHORITIES,
                claims = request.idToken.claims,
                idToken = request.idToken,
            )
            return authenticatedMember
        }
    }
}
