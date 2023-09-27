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

    public static final int MIN_LENGTH = 5;
    public static final int MAX_LENGTH = 20;

    private static final String EX_MESSAGE = String.format("스터디그룹 제목의 길이는 %d이상 %d이하이어야 합니다.", MIN_LENGTH, MAX_LENGTH);

    @Column(name = "title")
    private String value;

    public StudyGroupTitle(String value) {
        if (isValid(value)) {
            throw new IllegalArgumentException(EX_MESSAGE);
        }
        this.value = value;
    }

    public static boolean isValid(String value) {
        return value != null && value.length() >= MIN_LENGTH && value.length() <= MAX_LENGTH;
    }
}
