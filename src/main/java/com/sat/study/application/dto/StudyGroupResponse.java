package com.sat.study.application.dto;

import com.sat.study.domain.StudyGroup;

public record StudyGroupResponse(
        Long id,
        String title
) {
    public static StudyGroupResponse of(StudyGroup entity) {
        return new StudyGroupResponse(entity.getId(), entity.getInformation().getTitle().getValue());
    }
}
