package com.sat.study.domain;

import com.sat.common.validation.StringLengthValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StudyGroupContents {
    private static final StringLengthValidator STRING_LENGTH_VALIDATOR =
            new StringLengthValidator(10, 100);

    @Column(name = "contents")
    private String value;

    public StudyGroupContents(String contents) {
        STRING_LENGTH_VALIDATOR.validate(contents, String.format("스터디그룹 내용의 길이는 %d이상 %d이하이어야 합니다. 입력값: %s", 10, 100, contents));
        this.value = contents;
    }
}
