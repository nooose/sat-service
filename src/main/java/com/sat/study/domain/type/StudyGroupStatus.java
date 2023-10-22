package com.sat.study.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StudyGroupStatus {
    OPEN("OPEN", "모집 중"),
    CLOSED("CLOSED", "모집 종료"),
    PROCEEDING("PROCEEDING", "진행 중"),
    COMPLETE("COMPLETE", "완료");

    private final String code;
    private final String title;

    public boolean isOpen() {
        return this == OPEN;
    }
}
