package com.sat.common.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class SchedulingLogAspect {

    @Before("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void doBefore(JoinPoint joinPoint) {
        log.info("{} 스케줄러 시작", joinPoint.getSignature().toShortString());
    }

    @After("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void doAfter(JoinPoint joinPoint) {
        log.info("{} 스케줄러 종료", joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(value = "@annotation(org.springframework.scheduling.annotation.Scheduled)", throwing = "e")
    public void doThrowing(JoinPoint joinPoint, Exception e) {
        log.error("{} 스케줄러 예외", joinPoint.getSignature().toShortString(), e);
    }
}
