package com.sat.study.application.dto;

import com.sat.common.validation.StringLength;
import com.sat.study.domain.StudyCategory;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public record StudyGroupCreateRequest(
        @StringLength(min = 5, max = 20)
        String title,
        @StringLength(min = 10, max = 100)
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
