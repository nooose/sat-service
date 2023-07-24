package com.sat.study;

public enum StudyGroupStatus {
    OPEN, CLOSED, COMPLETE;

    public boolean isOpen() {
        return this == OPEN;
    }
}
