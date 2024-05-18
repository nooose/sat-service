package com.sat.common.utils

import com.sat.common.config.security.AuthenticatedMember
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User

fun SecurityContext.replacePrincipal(principal: OAuth2User) {
    val authentication = this.authentication as OAuth2AuthenticationToken
    this.authentication = OAuth2AuthenticationToken(
        principal,
        authentication.authorities,
        authentication.authorizedClientRegistrationId
    )
}

fun SecurityContext.principal(): AuthenticatedMember {
    return this.authentication.principal as AuthenticatedMember
}
