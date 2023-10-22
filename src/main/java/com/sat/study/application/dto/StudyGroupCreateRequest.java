package com.sat.study.application.dto;

import com.sat.member.domain.MemberId;
import com.sat.study.domain.type.StudyCategory;
import com.sat.study.domain.StudyGroup;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

public record StudyGroupCreateRequest(
        @StudyGroupTitle
        String title,
        String contents,
        StudyCategory category,
        int maxCapacity,
        @NotNull
        LocalDate startDate,
        @NotNull
        LocalDate endDate,
        Set<DayOfWeek> studyDays,
        int studyRounds,
        Duration timePerSession
) {

        public StudyGroup toEntity(MemberId memberId) {
                return StudyGroup.of(memberId,
                        title,
                        contents,
                        category,
                        maxCapacity,
                        startDate,
                        endDate,
                        studyDays,
                        studyRounds,
                        timePerSession
                );
        }
}
