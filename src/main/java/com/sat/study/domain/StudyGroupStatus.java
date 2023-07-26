package com.sat.study.domain;

public enum StudyGroupStatus {
    OPEN, CLOSED, COMPLETE;

    public boolean isOpen() {
        return this == OPEN;
    }
}
