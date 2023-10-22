package com.sat.study.domain;

import com.sat.member.domain.MemberId;
import com.sat.study.domain.type.ParticipantStatus;
import com.sat.study.domain.type.convertor.ParticipantStatusConvertor;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Embeddable
public class Participant {

    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "participant_id"))
    })
    @Embedded
    private MemberId id;
    @Convert(converter = ParticipantStatusConvertor.class)
    private ParticipantStatus status;

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

    public boolean matches(MemberId participantId) {
        return id.equals(participantId);
    }
}
