package com.sat.study.domain

import com.sat.study.domain.ParticipantType.BASIC
import com.sat.study.domain.ParticipantType.HOST
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import java.time.LocalDate

@DisplayName(name = "도메인 - 스터디그룹 테스트")
class StudyGroupTest : BehaviorSpec({

    Given("스터디그룹 참가자가 있고") {
        val notHost = Participant(1L, BASIC)
        val now = LocalDate.now()
        val period = Period(now, now.plusDays(7))

        When("참가자가 호스트가 아니면") {
            val exception = shouldThrow<IllegalArgumentException> {
                StudyGroup.open("테스트 그룹", "내용", period, notHost)
            }
            Then("예외를 던진다.") {
                val message = exception.message
                message shouldNotBe null
                println(message)
            }
        }
    }

    Given("스터디그룹이 있고") {
        val notHost = Participant(1L, HOST)
        val now = LocalDate.now()
        val period = Period(now, now.plusDays(7))
        val studyGroup = StudyGroup.open("테스트 그룹", "내용", period, notHost)

        When("추가 참가자가 호스트이면") {
            val exception = shouldThrow<IllegalArgumentException> {
                studyGroup.add(Participant(2L, HOST))
            }
            Then("예외를 던다.") {
                val message = exception.message
                message shouldNotBe null
                println(message)
            }
        }
    }
})
