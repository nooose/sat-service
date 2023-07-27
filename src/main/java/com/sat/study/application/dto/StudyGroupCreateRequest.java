package com.sat.study.application.dto;

import com.sat.study.domain.StudyCategory;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public record StudyGroupCreateRequest(
        String title,
        String contents,
        StudyCategory category,
        int maxCapacity,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        Set<DayOfWeek> studyDays,
        int studyRounds,
        Duration timePerSession
) {
}
