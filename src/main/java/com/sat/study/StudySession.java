package com.sat.study;

import java.time.Duration;

public class StudySession {

    private static final int SESSION_MIN_ROUND = 1;

    private int studyRounds;
    private Duration timePerSession;

    public StudySession(int studyRounds, Duration timePerSession) {
        if (studyRounds < SESSION_MIN_ROUND) {
            throw new IllegalArgumentException("스터디 하루 회차는 1번 이상이어야합니다.");
        }

        // TODO: 회차 시간이 하루를 넘지 않도록 검증

        this.studyRounds = studyRounds;
        this.timePerSession = timePerSession;
    }
}
