package com.sat.auth.config

import com.sat.auth.domain.JwtProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class JwtPropertiesTest extends Specification {

    @Autowired
    private JwtProperties jwtProperties


    def "jwt-yaml 파일에 있는 parameter를 불러온다."() {
        given:
        String actual비밀키 = "afakmnbnrioagenffewr!\$!@\$!!~~#noesingsaklgamslfksafmasfnafoiewnfalkfsdanklasghoasfjaoiwen"
        def expected비밀키 = jwtProperties.getSecretKey()

        when:
        actual비밀키.equals(expected비밀키)

        then:
        notThrown(Exception.class)
    }
}
