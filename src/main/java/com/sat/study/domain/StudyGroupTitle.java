package com.sat.study.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyGroupTitle {
    // TODO: 비속어 및 길이 제한
    private String title;

    public StudyGroupTitle(String title) {
        this.title = title;
    }
}
