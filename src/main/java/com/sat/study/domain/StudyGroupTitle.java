package com.sat.study.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyGroupTitle {
    // TODO: 비속어 및 길이 제한
    @Column(name = "title")
    private String value;

    public StudyGroupTitle(String value) {
        this.value = value;
    }
}
