package com.sat.common.utils

import com.sat.common.config.security.AuthenticatedMember
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

fun SecurityContext.principal(): AuthenticatedMember {
    return SecurityContextHolder.getContext().authentication.principal as AuthenticatedMember
}
