package com.sat.user.application

import com.sat.common.config.security.TokenConfigProperties
import com.sat.user.command.application.JwtProvider
import io.jsonwebtoken.ExpiredJwtException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.Duration
import java.util.*

@DisplayName(name = "JWT 테스트")
class JwtProviderTest : BehaviorSpec({
    val provider = JwtProvider(TokenConfigProperties("K".repeat(32), Duration.ZERO))

    Given("2000-10-10 만료된 키가 주어지고") {
        val expired = Date(971103600000)
        val token = provider.createToken("777", listOf(), expired)
        println(token)
        When("토큰을 검증하면") {
            val exception = shouldThrow<ExpiredJwtException> { provider.principalBy(token) }

            Then("만료 예외가 발생한다.") {
                exception.message shouldNotBe ""
                println(exception.message)
            }
        }
    }

    Given("2999-10-10 만료되지 않은 키가 주어지고") {
        val expired = Date(32496476400000)
        val token = provider.createToken("777", listOf(), expired)
        println(token)
        When("토큰에서 값을 꺼내면") {
            val principal = provider.principalBy(token)

            Then("토큰 사용자 식별자를 추출할 수 있다.") {
                principal shouldBe "777"
            }
        }
    }
})
