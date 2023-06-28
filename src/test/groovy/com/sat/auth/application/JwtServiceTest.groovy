package com.sat.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.sat.auth.config.JwtProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class JwtServiceTest extends Specification {

    @Autowired
    private JwtService jwtService

    @Autowired
    private JwtProperties jwtProperties

    def "Jwt 엑세스 토큰을 만든다."() {
        expect:
        def accessToken = jwtService.createAccessToken(12321321L)
        accessToken != null
    }

    def "Jwt 리프레시 토큰을 만든다"() {
        expect:
        def refreshToken = jwtService.createRefreshToken()
        refreshToken != null
    }

    def "access 토큰의 유효기간 만료 유무를 검사한다."() {
        given:
        def accessToken = jwtService.createAccessToken(12321321L)
        Thread.sleep(3000)

        when:
        jwtService.validExpiredTime(accessToken)

        then:
        thrown(TokenExpiredException.class)
    }

    void "access 토큰의 유효성을 검사한다.(유효하지 않은 경우)"() {
        given:
        Date now = new Date();
        String token = JWT.create()
                .withSubject("AccessToken")
                .withExpiresAt(new Date(now.getTime() + jwtProperties.access().expirationTime().toMillis()))
                .withClaim("socialId", 24332425L)
                .sign(Algorithm.HMAC512("24324wefffwfwffjafkjLKJ@#@*@#*%&*#U@JfSDHFh") )

        when:
        def valid = jwtService.isTokenValid(token)

        then:
        if (valid) {
            println "유효한 토큰입니다"
        } else {
            println "유효하지 않은 토큰입니다."
        }

    }

    void "access 토큰의 유효성을 검사한다.(유효한 경우)"() {
        given:
        def token = jwtService.createAccessToken(12321421412L)

        when:
        def valid = jwtService.isTokenValid(token)

        then:
        if (valid) {
            println "유효한 토큰입니다"
        } else {
            println "유효하지 않은 토큰입니다."
        }

    }

    def "refresh 토큰의 유효기간 만료 유무를 검사한다."() {
        given:
        def refreshToken = jwtService.createRefreshToken()
        Thread.sleep(7000)

        when:
        jwtService.validExpiredTime(refreshToken)

        then:
        thrown(TokenExpiredException.class)
    }

    void "refresh 토큰의 유효성을 검사한다.(유효하지 않은 경우)"() {
        given:
        Date now = new Date();
        String token = JWT.create()
                .withSubject("RefreshToken")
                .withExpiresAt(new Date(now.getTime() + jwtProperties.refresh().expirationTime().toMillis()))
                .sign(Algorithm.HMAC512("24324wefffwfwffjafkjLKJ@#@*@#*%&*#U@JfSDHFh") )

        when:
        def valid = jwtService.isTokenValid(token)

        then:
        if (valid) {
            println "유효한 토큰입니다"
        } else {
            println "유효하지 않은 토큰입니다."
        }

    }

}
