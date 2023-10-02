package com.sat.study.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class StudyTime {

    @Embedded
    private StudyPeriod period;

    @ElementCollection
    @CollectionTable(name = "study_days", joinColumns = @JoinColumn(name = "study_group_id"))
    private Set<DayOfWeek> studyDays = new HashSet<>();
    @Embedded
    private StudySession session;

    public StudyTime(LocalDate startDateTime, LocalDate endDateTime, Set<DayOfWeek> studyDays, int studyRounds, Duration timePerSession) {
        this(new StudyPeriod(startDateTime, endDateTime), studyDays, new StudySession(studyRounds, timePerSession));
    }

    public StudyTime(StudyPeriod period, Set<DayOfWeek> studyDays, StudySession session) {
        this.period = period;
        this.studyDays = studyDays;
        this.session = session;
    }
}
