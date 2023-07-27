package com.sat.study.application.dto;

public record ParticipantUpdateRequest(
        ParticipantRequestStatus status
) {

    public enum ParticipantRequestStatus {
        APPROVE, REJECT, KICK
    }
}
