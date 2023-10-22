package com.sat.common.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class SchedulingLogAspect {

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        log.info("스케줄러 시작 - {}", joinPoint.getSignature().toShortString());
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error("스케줄러 예외 - {}", joinPoint.getSignature().toShortString(), e);
        }
        log.info("스케줄러 종료 - {}", joinPoint.getSignature().toShortString());
        return result;
    }
}
