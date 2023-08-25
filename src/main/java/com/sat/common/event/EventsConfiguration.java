package com.sat.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class EventsConfiguration implements InitializingBean{

    private final ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        Events.setPublisher(applicationContext);
    }
}
