package com.sat.study


import com.sat.member.domain.MemberId
import spock.lang.Specification

import static com.sat.member.MemberFixtures.일반_사용자
import static com.sat.study.StudyGroupFixtures.getStudyGroupFixture

class StudyGroupTest extends Specification {

    private static final MemberId HOST_ID = 일반_사용자.getId()

    def "수용 인원은 최소 2명이다."() {
        when:
        def studyGroup = getStudyGroupFixture(HOST_ID, 1)

        then:
        thrown(IllegalArgumentException.class)
    }

    def "참여 요청을 할 수 있다."() {
        given:
        def studyGroup = getStudyGroupFixture(HOST_ID, 2)
        def participantId = MemberId.of("2000")

        when:
        studyGroup.requestJoin(participantId)

        then:
        studyGroup.fetchActiveMemberIds().size() == 1
    }

    def "스터디 그룹이 닫히면 참여 요청을 할 수 없다."() {
        given:
        def studyGroup = getStudyGroupFixture(HOST_ID, 2)
        def participantId = MemberId.of("2000")
        studyGroup.stopRecruitment(HOST_ID)

        when:
        studyGroup.requestJoin(participantId)

        then:
        thrown(RuntimeException.class)
    }

    def "같은 사용자가 중복 요청을 하면 예외를 던진다."() {
        given:
        def studyGroup = getStudyGroupFixture(HOST_ID, 2)
        def participantId = MemberId.of("2000")
        studyGroup.requestJoin(participantId)

        when:
        studyGroup.requestJoin(participantId)

        then:
        thrown(RuntimeException.class)
    }

    def "그룹장의 승인이 완료되면 스터디 그룹에 참여할 수 있다."() {
        given:
        def studyGroup = getStudyGroupFixture(HOST_ID, 2)
        def participantId = MemberId.of("2000")

        when:
        studyGroup.requestJoin(participantId)
        studyGroup.approve(HOST_ID, participantId)

        then:
        studyGroup.fetchActiveMemberIds().size() == 2
    }

    def "그룹장은 스터디 그룹 인원을 추방할 수 있다."() {
        given:
        def studyGroup = getStudyGroupFixture(HOST_ID, 2)
        def participantId = MemberId.of("2000")
        studyGroup.requestJoin(participantId)
        studyGroup.approve(HOST_ID, participantId)

        when:
        studyGroup.kick(HOST_ID, participantId)

        then:
        studyGroup.fetchActiveMemberIds().size() == 1
    }

    def "수용 인원을 초과하면 더 이상 참여자를 받을 수 없다."() {
        given:
        def studyGroup = getStudyGroupFixture(HOST_ID, 2)
        def participantAId = MemberId.of("2000")
        def participantBId = MemberId.of("3000")
        studyGroup.requestJoin(participantAId)
        studyGroup.requestJoin(participantBId)
        studyGroup.approve(HOST_ID, participantAId)

        when:
        studyGroup.approve(HOST_ID, participantBId)

        then:
        studyGroup.fetchActiveMemberIds().size() == 2
        thrown(RuntimeException.class)
    }
}
