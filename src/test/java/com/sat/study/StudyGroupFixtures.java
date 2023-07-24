package com.sat.study;

import com.sat.member.domain.MemberId;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class StudyGroupFixtures {
    public static StudyGroup getStudyGroupFixture(MemberId hostId,
                                                  int maxCapacity) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.plusDays(1);
        LocalDateTime endDate = now.plusDays(7);
        return StudyGroup.of(hostId,
                "테스트 제목", "테스트 내용", StudyCategory.IT,
                maxCapacity,
                startDate, endDate,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                4, Duration.ofHours(4)
        );
    }

    public static StudyGroup getStudyGroupFixture(MemberId hostId,
                                                  int maxCapacity,
                                                  int studyRounds,
                                                  Duration timePerSession) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = now.plusDays(1);
        LocalDateTime endDate = now.plusDays(7);
        return StudyGroup.of(hostId,
                "테스트 제목", "테스트 내용", StudyCategory.IT,
                maxCapacity,
                startDate, endDate,
                Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                studyRounds, timePerSession
        );
    }
}
