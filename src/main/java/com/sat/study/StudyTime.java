package com.sat.study;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyTime {

    @Embedded
    private StudyPeriod period;

    @ElementCollection
    @CollectionTable(name = "study_days", joinColumns = @JoinColumn(name = "study_group_id"))
    private Set<DayOfWeek> studyDays = new HashSet<>();
    @Embedded
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
