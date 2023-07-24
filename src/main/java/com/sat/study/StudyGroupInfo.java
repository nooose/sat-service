package com.sat.study;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class StudyGroupInfo {
    private StudyGroupTitle title;
    private StudyGroupContents contents;
    private StudyCategory studyCategory;
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
