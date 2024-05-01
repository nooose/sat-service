package com.sat.user.application

import com.sat.KEY
import com.sat.common.security.WithAuthenticatedUser
import com.sat.user.domain.port.PointRepository
import io.kotest.core.spec.DisplayName
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@DisplayName("Point 테스트")
@SpringBootTest(properties = ["jwt.secret-key=$KEY"])
class PointServiceTest @Autowired constructor(
    private val pointService: PointService,
    private val memberLoginService: MemberLoginService,
    private val pointRepository: PointRepository,
) {
    @WithAuthenticatedUser
    @Test
    fun `로그인 시 포인트를 적립해준다`() {
        // given
        val user = memberLoginService.login("성준혁", "aaa@google.com")

        // when
        val now = LocalDateTime.now()
        pointService.dailyPointAward(user.id!!, now.toLocalDate())
        memberLoginService.createLoginHistory(user.id!!, now)

        val oneHourLater = now.plusHours(1)
        pointService.dailyPointAward(user.id!!, oneHourLater.toLocalDate())
        memberLoginService.createLoginHistory(user.id!!, now)

        val oneDayLater = now.plusDays(1)
        pointService.dailyPointAward(user.id!!, oneDayLater.toLocalDate())
        memberLoginService.createLoginHistory(user.id!!, now)

        // then
        val userPoint = pointRepository.findByIdOrNull(user.id!!)
        assertThat(userPoint?.point).isEqualTo(20)
    }
}

