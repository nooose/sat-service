package com.sat.study.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyGroupContents {
    // TODO: 비속어 및 길이 제한
    @Column(name = "contents")
    private String value;

    public StudyGroupContents(String contents) {
        this.value = contents;
    }
}
