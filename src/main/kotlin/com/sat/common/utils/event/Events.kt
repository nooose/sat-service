package com.sat.common.utils.event

import org.springframework.context.ApplicationEventPublisher

object Events {
    private var publisher: ApplicationEventPublisher? = null

    fun initialize(publisher: ApplicationEventPublisher) {
        if (this.publisher == null) {
            this.publisher = publisher
        }
    }

    fun publish(event: Any) {
        publisher?.publishEvent(event)
    }
}
