package com.sat.study.domain;

import com.sat.study.domain.type.StudyCategory;
import com.sat.study.domain.type.convertor.StudyCategoryConvertor;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class StudyGroupInfo {
    @Embedded
    private StudyGroupTitle title;
    @Embedded
    private StudyGroupContents contents;
    @Convert(converter = StudyCategoryConvertor.class)
    private StudyCategory studyCategory;
    @Embedded
    private StudyTime time;

    public StudyGroupInfo(String title, String contents, StudyCategory category, LocalDate startDateTime, LocalDate endDateTime, Set<DayOfWeek> studyDays, int studyRounds, Duration timePerSession) {
        this(title, contents, category, new StudyTime(startDateTime, endDateTime, studyDays, studyRounds, timePerSession));
    }

    public StudyGroupInfo(String title, String contents, StudyCategory studyCategory, StudyTime time) {
        this.title = new StudyGroupTitle(title);
        this.contents = new StudyGroupContents(contents);
        this.studyCategory = studyCategory;
        this.time = time;
    }
}
