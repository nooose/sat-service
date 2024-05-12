package com.sat.event.application

import com.sat.event.domain.ConnectedNotification
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

        try {
            send(sseEmitter, ConnectedNotification())
        } catch (exception: IOException) {
            throw IllegalStateException("이벤트 구독 실패: $exception")
        }
        return sseEmitter
    }

    /**
     * - Event 이름: 알림 데이터 클래스 이름
     * @param memberId 알림 받을 사용자 ID
     * @param data 알림 데이터
     */
    fun <T : Any> send(memberId: Long, data: T) {
        val sseEmitter = emitterRepository.findBy(memberId) ?: return
        send(sseEmitter, data)
    }

    private fun send(sseEmitter: SseEmitter, data: Any) {
        sseEmitter.send(
            SseEmitter.event()
                .name(data::class.java.simpleName)
                .data(data)
        )
    }
}
