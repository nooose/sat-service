package com.sat.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sat.auth.config.JwtProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@SpringBootTest
class JwtServiceTest extends Specification {

    @Autowired
    private JwtService jwtService

    @Autowired
    private JwtProperties properties

    def "기간이 지난 토큰은 유효하지 않다고 판단한다"() {
        given:
        ZonedDateTime zonedDateTime = LocalDateTime.of(2023, 1, 1, 0, 0).atZone(ZoneId.systemDefault());
        def expiredDate = Date.from(zonedDateTime.toInstant())

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
