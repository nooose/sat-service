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
    private JwtProperties properties

    def "기간이 지난 토큰은 유효하지 않다고 판단한다"() {
        given:
        String token = JWT.create()
                .withSubject("test")
                .withExpiresAt(new Date())
                .sign(Algorithm.HMAC512(properties.secretKey()) )

        when:
        def valid = jwtService.isValidToken(token)

        then:
        !valid
    }
}
