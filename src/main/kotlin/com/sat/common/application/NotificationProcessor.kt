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
        emitterRepository.save(memberId, sseEmitter)

        try {
            val data = SseEmitter.event()
                .name("notification")
                .data(NotificationDto.connect())
            sseEmitter.send(data)
        } catch (exception: IOException) {
            throw IllegalStateException("이벤트 구독 실패: $exception")
        }
        sseEmitter.onCompletion { emitterRepository.delete(memberId) }
        sseEmitter.onTimeout { sseEmitter.complete() }
        return sseEmitter
    }

    fun send(memberId: Long, eventName: String, data: NotificationDto) {
        val sseEmitter = emitterRepository.findBy(memberId) ?: return
        sseEmitter.send(SseEmitter.event()
            .name("comment-notification")
            .data(data))
    }
}
