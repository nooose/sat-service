package com.sat.study.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class StudyGroupContents {

    @Column(name = "contents")
    private String value;

    public StudyGroupContents(String contents) {
        this.value = contents;
    }
}
