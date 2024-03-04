package com.sat.user.application

import com.sat.user.domain.Point
import com.sat.user.domain.port.LoginHistoryRepository
import com.sat.user.domain.port.PointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Transactional
@Service
class PointService(
    private val pointRepository: PointRepository,
    private val loginHistoryRepository: LoginHistoryRepository,
) {

    fun dailyPointAward(memberId: Long, today: LocalDate) {
        val point = pointRepository.findByMemberId(memberId)
        if (point == null) {
            pointRepository.save(Point(memberId))
            return
        }

        val todayZeroTime = LocalDateTime.of(today, LocalTime.of(0, 0))
        val existsTodayLoginHistory = loginHistoryRepository.existsByLoginDateTimeAfter(todayZeroTime)
        if (!existsTodayLoginHistory) {
            point.loginAward()
            log.info { "로그인 포인트 적립 완료 - $memberId" }
        }
    }
}
