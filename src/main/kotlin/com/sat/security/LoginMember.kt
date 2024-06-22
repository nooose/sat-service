package com.sat.security

import org.springframework.security.core.annotation.AuthenticationPrincipal

/**
 * @see AuthenticatedMember
 */
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@AuthenticationPrincipal
annotation class LoginMember
