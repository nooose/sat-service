package com.sat.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventsInitializer implements InitializingBean{

    private final ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        Events.setPublisher(applicationContext);
    }
}
