package com.sat.study

import com.sat.study.domain.StudyPeriod
import spock.lang.Specification

import java.time.LocalDateTime

class StudyPeriodTest extends Specification {

    def "과거 시간을 입력하면 예외를 던진다. [시작 시간: #startDate, 종료 시간: #endDate]"() {
        when:
        new StudyPeriod(startDate, endDate)

        then:
        thrown(IllegalArgumentException.class)

        where:
        startDate                        | endDate
        LocalDateTime.now().minusDays(1) | LocalDateTime.now().plusDays(1)
        LocalDateTime.now().plusDays(1)  | LocalDateTime.now().minusDays(1)
    }

    def "종료 시간이 시작 시간보다 빠르면 예외를 던진다."() {
        given:
        def now = LocalDateTime.now()
        LocalDateTime startDate = now.minusDays(1)
        LocalDateTime endDate = now.minusDays(2)

        when:
        new StudyPeriod(startDate, endDate)

        then:
        thrown(IllegalArgumentException.class)
    }
}
