package com.sat.study.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class StudyPeriod {

    private LocalDate startDate;
    private LocalDate endDate;

    public StudyPeriod(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    private static void validate(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (startDate.isBefore(now) || endDate.isBefore(now)) {
            throw new IllegalArgumentException("기간이 올바르지 않습니다.");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 빨라야합니다.");
        }
    }
}
