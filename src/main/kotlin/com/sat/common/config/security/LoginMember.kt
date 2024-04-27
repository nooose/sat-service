package com.sat.common.config.security

import org.springframework.security.core.annotation.AuthenticationPrincipal


@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@AuthenticationPrincipal
annotation class LoginMember
