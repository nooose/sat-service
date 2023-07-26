package com.sat.study;

import com.sat.member.domain.MemberId;
import com.sat.study.domain.Participant;
import com.sat.study.domain.ParticipantStatus;
import com.sat.study.domain.Participants;
import com.sat.study.domain.StudyCategory;
import com.sat.study.domain.StudyGroup;
import com.sat.study.domain.StudyGroupEnrollment;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sat.member.MemberFixtures.일반_사용자;
import static java.util.Objects.requireNonNullElse;

public class StudyGroupBuilder {

    private MemberId hostId;
    private String title;
    private String contents;
    private StudyCategory category;
    private int maxCapacity = 2;
    private StudyGroupEnrollment enrollment;
    private Set<Participant> participants = new HashSet<>();
    private final LocalDateTime now = LocalDateTime.now();
    private LocalDateTime startDateTime = now.plusDays(1);
    private LocalDateTime endDateTime = now.plusDays(7);
    private Set<DayOfWeek> studyDays;
    private int studyRounds = 4;
    private Duration timePerSession;

    public static StudyGroupBuilder builder() {
        return new StudyGroupBuilder();
    }

    public StudyGroupBuilder withHostId(MemberId hostId) {
        this.hostId = hostId;
        return this;
    }

    public StudyGroupBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public StudyGroupBuilder withContents(String contents) {
        this.contents = contents;
        return this;
    }

    public StudyGroupBuilder withCategory(StudyCategory category) {
        this.category = category;
        return this;
    }

    public StudyGroupBuilder withMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        return this;
    }

    public StudyGroupBuilder withPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        return this;
    }

    public StudyGroupBuilder withStudyDays(DayOfWeek... dayOfWeeks) {
        this.studyDays = Arrays.stream(dayOfWeeks).collect(Collectors.toSet());
        return this;
    }

    public StudyGroupBuilder withSession(int studyRounds, Duration timePerSession) {
        this.studyRounds = studyRounds;
        this.timePerSession = timePerSession;
        return this;
    }

    public StudyGroupBuilder withParticipants(ParticipantStatus status, String... memberIds) {
        Arrays.stream(memberIds)
                .map(MemberId::new)
                .forEach(it -> participants.add(new Participant(it, status)));
        return this;
    }

    public StudyGroup build() {
        return StudyGroup.of(requireNonNullElse(hostId, 일반_사용자.getId()),
                requireNonNullElse(title, "테스트 제목"),
                requireNonNullElse(contents, "테스트 내용"),
                requireNonNullElse(category, StudyCategory.IT),
                requireNonNullElse(enrollment, defaultStudyGroupEnrollment()),
                startDateTime, endDateTime,
                requireNonNullElse(studyDays,  Set.of(DayOfWeek.MONDAY)),
                studyRounds, requireNonNullElse(timePerSession, Duration.ofHours(1))
        );
    }

    private StudyGroupEnrollment defaultStudyGroupEnrollment() {
        if (participants.isEmpty()) {
            return new StudyGroupEnrollment(hostId, maxCapacity);
        }

        return new StudyGroupEnrollment(new Participants(Participant.host(hostId), participants));
    }
}
