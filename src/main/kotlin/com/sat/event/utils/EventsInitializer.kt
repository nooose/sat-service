package com.sat.event.utils

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class EventsInitializer(private val applicationContext: ApplicationContext) : InitializingBean {
    override fun afterPropertiesSet() {
        Events.initialize(applicationContext)
    }
}
