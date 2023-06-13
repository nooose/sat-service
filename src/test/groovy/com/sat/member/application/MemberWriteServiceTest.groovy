package com.sat.member.application

import com.sat.member.application.dto.MemberRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration
@Transactional
@SpringBootTest
class MemberWriteServiceTest extends Specification {

    @Autowired
    private MemberWriteService memberWriteService

    def "같은 이름을 가진 사용자를 생성할 수 없다."() {
        given:
        def request = new MemberRequest("noose")
        memberWriteService.join(request)

        when:
        memberWriteService.join(request)

        then:
        def e = thrown(IllegalArgumentException.class)
        e.getMessage() == "noose은(는) 중복된 이름입니다."
    }
}
