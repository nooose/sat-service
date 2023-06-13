package com.sat.member.domain

import spock.lang.Specification

class NameTest extends Specification {

    def "2글자 이상 10글자 이하의 이름을 생성할 수 있다. [입력값: #name]"() {
        when:
        new Name(name)

        then:
        notThrown(Exception.class)

        where:
        name                    |   _
        "안녕"                  |   _
        "noose"                 |   _
        "hello"                 |   _
        "안녕하세요안녕하세요"  |   _
    }

    def "2글자 미만이거나 10글자 초과라면 예외를 던진다. [입력값: #name]"() {
        when:
        new Name(name)

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "이름은 2글자 이상 10글자 이하이어야 합니다."

        where:
        name            |   _
        "A"             |   _
        "A".repeat(11)  |   _
    }

    def "특수문자가 포함되면 예외를 던진다. [입력값: #name]"() {
        when:
        new Name(name)

        then:
        def e = thrown(IllegalArgumentException.class)
        e.message == "특수문자는 포함할 수 없습니다."

        where:
        name        |   _
        "안녕?"     |   _
        "###"       |   _
        "noo\$e"    |   _
        "\\\\"      |   _
    }
}
