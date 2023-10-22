package com.sat.study.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StudyCategory {

    // TODO: 다양한 카테고리 추가
    IT("IT", "IT"), FOREIGN_LANGUAGE("FOREIGN_LANGUAGE", "외국어");

    private final String code;
    private final String title;

}
