package com.sat.common.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser

data class AuthenticatedMember(
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

    companion object {
        fun of(request: OidcUserRequest, roles: Collection<GrantedAuthority>): OidcUser {
            return AuthenticatedMember(
                name = request.idToken.nickName as String,
                authorities = roles,
                claims = request.idToken.claims,
                idToken = request.idToken,
            )
        }
    }
}
