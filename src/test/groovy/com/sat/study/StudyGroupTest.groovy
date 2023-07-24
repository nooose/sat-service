package com.sat.study


import com.sat.member.domain.MemberId
import spock.lang.Specification

import static com.sat.member.MemberFixtures.일반_사용자
import static com.sat.study.ParticipantStatus.APPROVED
import static com.sat.study.ParticipantStatus.WAITING

class StudyGroupTest extends Specification {

    private static final MemberId HOST_ID = 일반_사용자.getId()

    def "수용 인원은 최소 2명이다."() {
        when:
        def studyGroup = StudyGroupBuilder.builder()
        .withHostId(HOST_ID)
        .withMaxCapacity(1)
        .build()

        then:
        thrown(IllegalArgumentException.class)
    }

    def "참여 요청을 할 수 있다."() {
        given:
        def studyGroup = StudyGroupBuilder.builder()
                .withHostId(HOST_ID)
                .build()

        when:
        studyGroup.requestJoin(MemberId.of("2000"))

        then:
        studyGroup.fetchActiveMemberIds().size() == 1
    }

    def "스터디 그룹이 닫히면 참여 요청을 할 수 없다."() {
        given:
        def studyGroup = StudyGroupBuilder.builder()
                .withHostId(HOST_ID)
                .build()
        studyGroup.stopRecruitment(HOST_ID)

        when:
        studyGroup.requestJoin(MemberId.of("2000"))

        then:
        thrown(RuntimeException.class)
    }

    def "같은 사용자가 중복 요청을 하면 예외를 던진다."() {
        given:
        def studyGroup = StudyGroupBuilder.builder()
                .withHostId(HOST_ID)
                .withParticipants(WAITING, "2000")
                .build()

        when:
        studyGroup.requestJoin(MemberId.of("2000"))

        then:
        thrown(RuntimeException.class)
    }

    def "그룹장의 승인이 완료되면 스터디 그룹에 참여할 수 있다."() {
        given:
        def studyGroup = StudyGroupBuilder.builder()
                .withHostId(HOST_ID)
                .withParticipants(WAITING, "2000")
                .build()

        when:
        studyGroup.approve(HOST_ID, MemberId.of("2000"))

        then:
        studyGroup.fetchActiveMemberIds().size() == 2
    }

    def "그룹장은 스터디 그룹 인원을 추방할 수 있다."() {
        given:
        def studyGroup = StudyGroupBuilder.builder()
                .withHostId(HOST_ID)
                .withParticipants(APPROVED, "2000")
                .build()

        when:
        studyGroup.kick(HOST_ID, MemberId.of("2000"))

        then:
        studyGroup.fetchActiveMemberIds().size() == 1
    }

    def "수용 인원을 초과하면 더 이상 참여자를 받을 수 없다."() {
        given:
        def studyGroup = StudyGroupBuilder.builder()
                .withHostId(HOST_ID)
                .withMaxCapacity(2)
                .withParticipants(APPROVED, "2000")
                .withParticipants(WAITING, "3000")
                .build()

        when:
        studyGroup.approve(HOST_ID, MemberId.of("3000"))

        then:
        studyGroup.fetchActiveMemberIds().size() == 2
        thrown(RuntimeException.class)
    }
}
