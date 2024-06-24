package com.sat.common.config.jpa

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class TransactionalFunAspect {
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    fun before(joinPoint: JoinPoint) {
        val methodName = joinPoint.signature.name
        CurrentFunNameHolder.funName = methodName
    }

    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    fun after(joinPoint: JoinPoint) {
        CurrentFunNameHolder.clear()
    }
}

