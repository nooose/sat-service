package com.sat.study;

import com.sat.member.domain.MemberId;

public class StudyGroupEnrollment {

    private static final int MIN_CAPACITY = 2;
    private static final int MAX_CAPACITY = 10;

    private Participants participants;
    private int maxCapacity;

    public StudyGroupEnrollment(MemberId hostId, int maxCapacity) {
        this(new Participants(Participant.host(hostId)), maxCapacity);
    }

    public StudyGroupEnrollment(Participants participants) {
        this(participants, MIN_CAPACITY);
    }

    public StudyGroupEnrollment(Participants participants, int maxCapacity) {
        if (maxCapacity < MIN_CAPACITY || maxCapacity > MAX_CAPACITY) {
            throw new IllegalArgumentException(String.format("최대 인원은 %d명 이상 %d이하이어야 합니다.", MIN_CAPACITY, MAX_CAPACITY));
        }
        this.participants = participants;
        this.maxCapacity = maxCapacity;
    }

    public void request(MemberId participantId) {
        participants.add(new Participant(participantId));
    }

    public void enroll(MemberId participantId) {
        if (maxCapacity == participants.activeMembersCount()) {
            throw new IllegalArgumentException(String.format("최대 수용 인원은 %d명까지입니다.", maxCapacity));
        }

        participants.approve(participantId);
    }

    public void reject(MemberId participantId) {
        participants.reject(participantId);
    }

    public void cancel(MemberId participantId) {
        participants.remove(participantId);
    }


    public void block(MemberId participantId) {
        participants.block(participantId);
    }
}
