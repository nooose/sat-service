package com.sat.study.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ParticipantStatus implements CodeEnum {
    WAITING("WAITING", "대기"),
    APPROVED("APPROVED", "승인"),
    REJECTED("REJECTED", "거절"),
    BLOCKED("BLOCKED", "추방");

    private final String code;
    private final String title;
}
