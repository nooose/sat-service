package com.sat.common.utils.event

import org.springframework.context.ApplicationEventPublisher

object Events {
    private lateinit var publisher: ApplicationEventPublisher

    fun initialize(publisher: ApplicationEventPublisher) {
        if (publisher == null) {
            this.publisher = publisher
        }
    }

    fun publish(event: Any) {
        if (publisher != null) {
            publisher.publishEvent(event)
        }
    }
}
