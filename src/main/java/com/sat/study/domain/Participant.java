package com.sat.study.domain;

import com.sat.member.domain.MemberId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor()
@Embeddable
public class Participant {

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "participant_id"))
    })
    @Embedded
    private MemberId id;
    @Enumerated(EnumType.STRING)
    private ParticipantStatus status;

    public static Participant host(MemberId hostId) {
        return new Participant(hostId, ParticipantStatus.HOST);
    }

    public Participant(MemberId id) {
        this(id, ParticipantStatus.WAITING);
    }

    public Participant(MemberId id, ParticipantStatus status) {
        this.id = id;
        this.status = status;
    }

    public void approved() {
        status = ParticipantStatus.APPROVED;
    }

    public void rejected() {
        status = ParticipantStatus.REJECTED;
    }

    public void blocked() {
        status = ParticipantStatus.BLOCKED;
    }

    public boolean isActive() {
        return status == ParticipantStatus.APPROVED;
    }

    public boolean isHostOrActive() {
        return status == ParticipantStatus.HOST || status == ParticipantStatus.APPROVED;
    }

    public boolean matches(MemberId participantId) {
        return id.equals(participantId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
