package com.sat.member.domain

import spock.lang.Specification

class MemberTest extends Specification {

    def "사용자를 이름으로 생성할 수 있다."() {
        when:
        new Member("noose")

        then:
        notThrown(Exception.class)
    }
}
