package com.sat.auth.config.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sat.auth.config.application.JwtProcessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.ZonedDateTime

@SpringBootTest
class JwtProcessorTest extends Specification {

    @Autowired
    private JwtProcessor jwtService

    @Autowired
    private JwtProperties properties

    def "기간이 지난 토큰은 유효하지 않다고 판단한다"() {
        given:
        def expiredDate = Date.from(ZonedDateTime.now().minusHours(1).toInstant())
        String token = JWT.create()
                .withSubject("test")
                .withExpiresAt(expiredDate)
                .sign(Algorithm.HMAC512(properties.secretKey()))

        when:
        def valid = jwtService.isValidToken(token)

        then:
        !valid
    }

    def "서명키가 다르면 유효하지 않다고 판단한다."() {
        given:
        String token = JWT.create()
                .withSubject("test")
                .sign(Algorithm.HMAC512("test"))

        when:
        def valid = jwtService.isValidToken(token)

        then:
        !valid
    }
}
