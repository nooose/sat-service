package com.sat.event.application

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class EmitterRepository(
    private val emitterMap: MutableMap<Long, SseEmitter> = ConcurrentHashMap()
) {

    fun save(memberId: Long, sseEmitter: SseEmitter) {
        emitterMap[memberId] = sseEmitter
    }

    fun findBy(memberId: Long): SseEmitter? {
        return emitterMap[memberId]
    }

    fun delete(memberId: Long) {
        emitterMap.remove(memberId)
    }
}
