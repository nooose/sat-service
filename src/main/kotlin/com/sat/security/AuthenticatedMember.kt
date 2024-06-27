package com.sat.security

import com.sat.user.command.domain.member.Member
import com.sat.user.command.domain.member.RoleType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser

data class AuthenticatedMember(
    val id: Long,
    private val name: String,
    val nickname: String,
    private val email: String,
    val avatar: String,
    val roles: List<RoleType>,
    private val idToken: OidcIdToken,
) : OidcUser {
    override fun getName(): String {
        return name
    }

    override fun getAttributes(): Map<String, Any> {
        return mapOf()
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(roles.map { "ROLE_$it" })
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
        return email
    }

    companion object {
        fun from(request: OidcUserRequest, loginMember: Member): AuthenticatedMember {
            return AuthenticatedMember(
                id = loginMember.id,
                name = loginMember.name,
                nickname = loginMember.nickname,
                email = request.idToken.email as String,
                avatar = request.idToken.claims["picture"] as String,
                roles = listOf(loginMember.role.type),
                idToken = request.idToken,
            )
        }
    }
}
