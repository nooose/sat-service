package com.sat.common.config.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public SchedulingLogAspect schedulingLogAspect() {
        return new SchedulingLogAspect();
    }
}
