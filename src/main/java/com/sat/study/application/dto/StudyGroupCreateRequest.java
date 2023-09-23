package com.sat.study.application.dto;

import com.sat.study.domain.StudyCategory;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public record StudyGroupCreateRequest(
        @StudyGroupTitle
        String title,
        String contents,
        StudyCategory category,
        int maxCapacity,
        @NotNull
        LocalDateTime startDateTime,
        @NotNull
        LocalDateTime endDateTime,
        Set<DayOfWeek> studyDays,
        int studyRounds,
        Duration timePerSession
) {
}
