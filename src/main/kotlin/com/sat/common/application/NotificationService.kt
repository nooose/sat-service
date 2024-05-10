package com.sat.common.application

import com.sat.common.domain.dto.query.NotificationQuery
import com.sat.common.infrastructure.EmitterRepository
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.time.Duration

private val DEFAULT_TIMEOUT = Duration.ofHours(1).toMillis()

@Service
class NotificationService(
    private val emitterRepository: EmitterRepository
) {

    fun connect(memberId: Long): SseEmitter {
        val sseEmitter = SseEmitter(DEFAULT_TIMEOUT)
        emitterRepository.save(memberId, sseEmitter)

        try {
            val data = SseEmitter.event()
                .name("notification")
                .data(NotificationQuery.connect())
            sseEmitter.send(data)
        } catch (exception: IOException) {
            println(exception)
            throw IllegalStateException("이벤트 구독 실패: $exception")
        }
        sseEmitter.onCompletion {
            println("completion")
            emitterRepository.delete(memberId)
        }
        sseEmitter.onTimeout {
            println("timeout")
            sseEmitter.complete()
        }
        return sseEmitter
    }
}
