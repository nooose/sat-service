package com.sat.common.application

import com.sat.common.infrastructure.EmitterRepository
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.time.Duration

private val DEFAULT_TIMEOUT = Duration.ofHours(1).toMillis()

@Component
class NotificationProcessor(
    private val emitterRepository: EmitterRepository,
) {

    fun connect(memberId: Long): SseEmitter {
        val sseEmitter = SseEmitter(DEFAULT_TIMEOUT)
        sseEmitter.onCompletion { emitterRepository.delete(memberId) }
        sseEmitter.onTimeout { sseEmitter.complete() }
        sseEmitter.onError { sseEmitter.complete() }
        emitterRepository.save(memberId, sseEmitter)

        val data = SseEmitter.event()
            .name("notification")
            .data(NotificationDto.connect())
        try {
            sseEmitter.send(data)
        } catch (exception: IOException) {
            throw IllegalStateException("이벤트 구독 실패: $exception")
        }
        return sseEmitter
    }

    fun <T> send(memberId: Long, eventName: String, data: NotificationDto<T>) {
        val sseEmitter = emitterRepository.findBy(memberId) ?: return
        sseEmitter.send(
            SseEmitter.event()
            .name(eventName)
            .data(data)
        )
    }
}
