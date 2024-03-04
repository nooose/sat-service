package com.sat.common.utils.event

import org.springframework.context.ApplicationEventPublisher

object Events {
    private var publisher: ApplicationEventPublisher? = null

    fun initialize(publisher: ApplicationEventPublisher) {
        check(this.publisher == null) { "ApplicationEventPublisher 를 다시 할당할 수 없습니다." }
        this.publisher = publisher
    }

    fun publish(event: Any) {
        if (publisher != null) {
            publisher!!.publishEvent(event)
        }
    }
}
