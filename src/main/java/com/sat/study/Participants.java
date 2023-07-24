package com.sat.study;

import com.sat.member.domain.MemberId;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Participants {
    private Set<Participant> participants = new HashSet<>();

    public Participants(Participant host) {
        participants.add(host);
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
                .filter(Participant::isActive)
                .toList();
    }
}
