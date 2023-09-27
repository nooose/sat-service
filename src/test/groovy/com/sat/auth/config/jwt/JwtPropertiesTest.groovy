package com.sat.auth.config.jwt


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class JwtPropertiesTest extends Specification {

    @Autowired
    private JwtProperties jwtProperties


    def "프로퍼티 바인딩 작업이 정상 작동된다."() {
        expect:
        def secretKey = jwtProperties.secretKey()
        secretKey != null
    }
}
