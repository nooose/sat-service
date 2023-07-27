package com.sat.study.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyGroupInfo {
    @Embedded
    private StudyGroupTitle title;
    @Embedded
    private StudyGroupContents contents;
    @Enumerated(EnumType.STRING)
    private StudyCategory studyCategory;
    @Embedded
    private StudyTime time;

    public StudyGroupInfo(String title, String contents, StudyCategory category, LocalDateTime startDateTime, LocalDateTime endDateTime, Set<DayOfWeek> studyDays, int studyRounds, Duration timePerSession) {
        this(title, contents, category, new StudyTime(startDateTime, endDateTime, studyDays, studyRounds, timePerSession));
    }

    public StudyGroupInfo(String title, String contents, StudyCategory studyCategory, StudyTime time) {
        this.title = new StudyGroupTitle(title);
        this.contents = new StudyGroupContents(contents);
        this.studyCategory = studyCategory;
        this.time = time;
    }
}
