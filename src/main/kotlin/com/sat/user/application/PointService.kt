package com.sat.user.application

import com.sat.board.domain.port.ArticleRepository
import com.sat.common.utils.findByIdOrThrow
import com.sat.user.domain.Point
import com.sat.user.domain.PointType
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
    private val articleRepository: ArticleRepository,
) {

    fun dailyPointAward(memberId: Long, today: LocalDate, pointType: PointType) {
        val todayZeroTime = LocalDateTime.of(today, LocalTime.of(0, 0))
        val existsTodayLoginHistory = loginHistoryRepository.existsByLoginDateTimeAfter(todayZeroTime)
        if (!existsTodayLoginHistory) {
            val loginPoint = Point(memberId, pointType.score)
            pointRepository.save(loginPoint)
            log.info { "로그인 포인트 적립 완료 - $memberId" }
        }
    }

    fun commentPointAward(articleId: Long, memberId: Long, pointType: PointType) {
        val article = articleRepository.findByIdOrThrow(articleId) { throw IllegalArgumentException("존재하지 않는 게시물 입니다.") }
        if (article.isOwner(memberId)) {
            return
        }
        val commentPoint = Point(memberId, pointType.score)
        pointRepository.save(commentPoint)
    }

    fun articlePointAward(memberId: Long, pointType: PointType) {
        val articlePoint = Point(memberId, pointType.score)
        pointRepository.save(articlePoint)
    }
}
