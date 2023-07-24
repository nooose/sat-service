package com.sat.study;

import java.time.LocalDateTime;

public class StudyPeriod {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public StudyPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime now = LocalDateTime.now();
        if (startDateTime.isBefore(now) || endDateTime.isBefore(now)) {
            throw new IllegalArgumentException("기간이 올바르지 않습니다.");
        }

        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 빨라야합니다.");
        }

        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
