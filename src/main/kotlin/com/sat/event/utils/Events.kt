package com.sat.event.utils

import org.springframework.context.ApplicationEventPublisher

object Events {
    private var publisher: ApplicationEventPublisher? = null

    fun initialize(publisher: ApplicationEventPublisher) {
        if (Events.publisher == null) {
            Events.publisher = publisher
        }
    }

    fun publish(event: Any) {
        publisher?.publishEvent(event)
    }
}
