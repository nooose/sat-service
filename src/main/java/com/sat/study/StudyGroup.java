package com.sat.study;

import com.sat.member.domain.MemberId;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class StudyGroup {

    private Long id;
    private Host host;
    private StudyGroupInfo information;
    private StudyGroupEnrollment enrollment;
    private StudyGroupStatus status;

    private StudyGroup(Host host, StudyGroupInfo information, int maxCapacity) {
        this(null, host, information, new StudyGroupEnrollment(host.getId(), maxCapacity), StudyGroupStatus.OPEN);
    }

    private StudyGroup(Long id, Host host, StudyGroupInfo information, StudyGroupEnrollment enrollment, StudyGroupStatus status) {
        this.id = id;
        this.host = host;
        this.information = information;
        this.enrollment = enrollment;
        this.status = status;
    }

    public static StudyGroup of(MemberId hostId,
                                String title,
                                String contents,
                                StudyCategory category,
                                int maxCapacity,
                                LocalDateTime startDateTime,
                                LocalDateTime endDateTime,
                                Set<DayOfWeek> studyDays,
                                int studyRounds,
                                Duration timePerSession) {

        StudyGroupInfo information = new StudyGroupInfo(title, contents, category, startDateTime, endDateTime, studyDays, studyRounds, timePerSession);
        return new StudyGroup(new Host(hostId), information, maxCapacity);
    }

    public void requestJoin(MemberId participantId) {
        if (!status.isOpen()) {
            throw new RuntimeException();
        }

        enrollment.request(participantId);
    }

    public void cancel(MemberId participantId) {
        enrollment.cancel(participantId);
    }

    public void approve(MemberId hostId, MemberId participantId) {
        validateOwner(hostId);
        enrollment.enroll(participantId);
    }

    public void reject(MemberId hostId, MemberId participantId) {
        validateOwner(hostId);
        enrollment.reject(participantId);
    }

    public void kick(MemberId hostId, MemberId participantId) {
        validateOwner(hostId);
        enrollment.block(participantId);
    }

    public void openRecruitment(MemberId hostId) {
        validateOwner(hostId);
        status = StudyGroupStatus.OPEN;
    }

    public void stopRecruitment(MemberId hostId) {
        validateOwner(hostId);
        status = StudyGroupStatus.CLOSED;
    }

    private void validateOwner(MemberId hostId) {
        if (!host.isOwner(hostId)) {
            throw new RuntimeException();
        }
    }

    public List<MemberId> fetchActiveMemberIds() {
        return enrollment.activeMemberIds();
    }
}
