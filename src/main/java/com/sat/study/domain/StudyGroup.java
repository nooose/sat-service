package com.sat.study.domain;

import com.sat.member.domain.MemberId;
import com.sat.study.domain.type.StudyCategory;
import com.sat.study.domain.type.StudyGroupStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class StudyGroup {

    @Column(name = "study_group_id")
    @Id @GeneratedValue
    private Long id;
    @Embedded
    private Host host;
    @Embedded
    private StudyGroupInfo information;
    @Embedded
    private StudyGroupEnrollment enrollment;
    @Enumerated(EnumType.STRING)
    private StudyGroupStatus status;

    private StudyGroup(Host host, StudyGroupInfo information, StudyGroupEnrollment enrollment) {
        this(null, host, information, enrollment, StudyGroupStatus.OPEN);
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

    public static StudyGroup of(MemberId hostId,
                                String title,
                                String contents,
                                StudyCategory category,
                                StudyGroupEnrollment enrollment,
                                LocalDate startDate,
                                LocalDate endDate,
                                Set<DayOfWeek> studyDays,
                                int studyRounds,
                                Duration timePerSession) {

        StudyGroupInfo information = new StudyGroupInfo(title, contents, category, startDate, endDate, studyDays, studyRounds, timePerSession);
        return new StudyGroup(new Host(hostId), information, enrollment);
    }

    public static StudyGroup of(MemberId hostId,
                                String title,
                                String contents,
                                StudyCategory category,
                                int maxCapacity,
                                LocalDate startDate,
                                LocalDate endDate,
                                Set<DayOfWeek> studyDays,
                                int studyRounds,
                                Duration timePerSession) {

        StudyGroupInfo information = new StudyGroupInfo(title, contents, category, startDate, endDate, studyDays, studyRounds, timePerSession);
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
