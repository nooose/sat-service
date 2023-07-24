package com.sat.study;

import com.sat.member.domain.MemberId;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

public class StudyGroup {

    private Long id;
    private Host host;
    private StudyGroupInfo information;
    private StudyGroupEnrollment enrollment;
    private StudyGroupStatus status;

    public StudyGroup of(MemberId hostId,
                         int maxCapacity,
                         String title,
                         String contents,
                         StudyCategory category,
                         LocalDateTime startDateTime,
                         LocalDateTime endDateTime,
                         Set<DayOfWeek> studyDays,
                         int studyRounds,
                         Duration timePerSession) {

        StudyGroupInfo information = new StudyGroupInfo(title, contents, category, startDateTime, endDateTime, studyDays, studyRounds, timePerSession);
        return new StudyGroup(new Host(hostId), information, maxCapacity);
    }

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
        status = StudyGroupStatus.OPEN;
    }


    private void validateOwner(MemberId hostId) {
        if (!host.isOwner(hostId)) {
            throw new RuntimeException();
        }
    }
}
