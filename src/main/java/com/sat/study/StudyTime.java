package com.sat.study;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class StudyTime {
    private StudyPeriod period;
    private Set<DayOfWeek> studyDays;
    private StudySession session;


    public StudyTime(LocalDateTime startDateTime, LocalDateTime endDateTime, Set<DayOfWeek> studyDays, int studyRounds, Duration timePerSession) {
        this(new StudyPeriod(startDateTime, endDateTime), studyDays, new StudySession(studyRounds, timePerSession));
    }

    public StudyTime(StudyPeriod period, Set<DayOfWeek> studyDays, StudySession session) {
        this.period = period;
        this.studyDays = studyDays;
        this.session = session;
    }
}
