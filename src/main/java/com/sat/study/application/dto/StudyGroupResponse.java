package com.sat.study.application.dto;

import com.sat.study.domain.StudyGroup;

import java.time.LocalDate;

public record StudyGroupResponse(
        Long id,
        String title,
        String contents,
        LocalDate startDate,
        LocalDate endDate
) {
    public static StudyGroupResponse from(StudyGroup entity) {
        return new StudyGroupResponse(
                entity.getId(),
                entity.getInformation().getTitle().getValue(),
                entity.getInformation().getContents().getValue(),
                entity.getInformation().getTime().getPeriod().getStartDate(),
                entity.getInformation().getTime().getPeriod().getEndDate()
        );
    }
}
