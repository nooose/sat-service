package com.sat.study.domain;

import com.sat.common.validation.StringLengthValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyGroupTitle {
    private static final StringLengthValidator STRING_LENGTH_VALIDATOR =
            new StringLengthValidator(5, 20);

    @Column(name = "title")
    private String value;

    public StudyGroupTitle(String value) {
        STRING_LENGTH_VALIDATOR.validate(value, String.format("스터디그룹 제목의 길이는 %d이상 %d이하이어야 합니다. 입력값: %s", 5, 20, value));
        this.value = value;
    }
}
