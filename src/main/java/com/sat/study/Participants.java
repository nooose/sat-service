package com.sat.study;

import com.sat.member.domain.MemberId;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Participants {

    @ElementCollection
    @CollectionTable(name = "participant", joinColumns = @JoinColumn(name = "study_group_id"))
    private Set<Participant> participants = new HashSet<>();

    public Participants(Participant host) {
        participants.add(host);
    }

    public Participants(Participant host, Collection<Participant> participants) {
        ArrayList<Participant> initializedParticipants = new ArrayList<>(participants);
        initializedParticipants.add(host);
        this.participants = new HashSet<>(initializedParticipants);
    }

    public int activeMembersCount() {
        return activeMembers().size();
    }

    public void add(Participant participant) {
        participants.add(participant);
    }

    public boolean isPresent(MemberId participantId) {
        return participants.stream()
                .anyMatch(it -> it.matches(participantId));
    }

    public void approve(MemberId participantId) {
        Participant participant = find(participantId);
        participant.approved();
    }

    public void reject(MemberId participantId) {
        Participant participant = find(participantId);
        participant.rejected();
    }

    public void remove(MemberId participantId) {
        Participant participant = find(participantId);
        participants.remove(participant);
    }

    public void block(MemberId participantId) {
        Participant participant = find(participantId);
        participant.blocked();
    }

    private Participant find(MemberId participantId) {
        return participants.stream()
                .filter(participant -> participant.matches(participantId))
                .findAny()
                .orElseThrow();
    }

    public List<Participant> activeMembers() {
        return participants.stream()
                .filter(Participant::isHostOrActive)
                .toList();
    }
}
