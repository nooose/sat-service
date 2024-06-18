package com.sat.user.command.application

import com.sat.board.domain.port.ArticleRepository
import com.sat.common.utils.findByIdOrThrow
import com.sat.common.utils.toZeroTime
import com.sat.user.command.domain.member.LoginHistoryRepository
import com.sat.user.command.domain.point.Point
import com.sat.user.command.domain.point.PointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@Service
class PointCommandService(
    private val pointRepository: PointRepository,
    private val loginHistoryRepository: LoginHistoryRepository,
    private val articleRepository: ArticleRepository,
    private val memberLoginService: MemberLoginService,
) {
    fun dailyPointAward(memberId: Long, today: LocalDateTime) {
        if (!existsTodayLoginHistory(today)) {
            val loginPoint = Point.login(memberId)
            pointRepository.save(loginPoint)
            log.info { "로그인 포인트 적립 완료 - $memberId" }
        }
        memberLoginService.createLoginHistory(memberId, today)
    }

    private fun existsTodayLoginHistory(today: LocalDateTime): Boolean {
        val zeroTime = today.toZeroTime()
        return loginHistoryRepository.existsByLoginDateTimeAfter(zeroTime)
    }

    fun commentPointAward(articleId: Long, principalId: Long) {
        val article = articleRepository.findByIdOrThrow(articleId) { throw IllegalArgumentException("존재하지 않는 게시물 입니다.") }
        if (article.isOwner(principalId)) {
            return
        }
        val commentPoint = Point.comment(principalId)
        pointRepository.save(commentPoint)
    }

    /**
     * 게시글을 생성하면 포인트를 적립시키는 함수
     */
    fun articlePointAward(principalId: Long) {
        val articlePoint = Point.article(principalId)
        pointRepository.save(articlePoint)
    }
}
