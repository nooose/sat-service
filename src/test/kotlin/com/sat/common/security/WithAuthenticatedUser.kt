package com.sat.common.security

import org.springframework.security.test.context.support.TestExecutionEvent
import org.springframework.security.test.context.support.WithSecurityContext


@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockAuthenticatedUserSecurityContextFactory::class, setupBefore = TestExecutionEvent.TEST_EXECUTION)
annotation class WithAuthenticatedUser(
    val id: Long = 1L,
    val name: String = "김영철",
    val nickname: String = "김영철",
    val email: String = "admin@sat.com",
)
