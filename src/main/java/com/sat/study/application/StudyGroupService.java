package com.sat.study.application;

import com.sat.common.exception.DataNotFoundException;
import com.sat.member.domain.MemberId;
import com.sat.study.application.dto.ParticipantUpdateRequest;
import com.sat.study.application.dto.StudyGroupCreateRequest;
import com.sat.study.application.dto.StudyGroupResponse;
import com.sat.study.domain.StudyGroup;
import com.sat.study.infrastructure.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;

    @Transactional
    public StudyGroupResponse create(String hostId, StudyGroupCreateRequest request) {
        StudyGroup studyGroup = StudyGroup.of(MemberId.of(hostId),
                request.title(), request.contents(), request.category(),
                request.maxCapacity(),
                request.startDateTime(), request.endDateTime(),
                request.studyDays(), request.studyRounds(), request.timePerSession());
        studyGroupRepository.save(studyGroup);
        return StudyGroupResponse.of(studyGroup);
    }

    public List<StudyGroupResponse> findAll() {
        return studyGroupRepository.findAll()
                .stream()
                .map(StudyGroupResponse::of)
                .toList();
    }

    @Transactional
    public void requestJoin(Long studyGroupId, String participantId) {
        StudyGroup studyGroup = getStudyGroup(studyGroupId);
        studyGroup.requestJoin(MemberId.of(participantId));
    }

    @Transactional
    public void updateStatus(String hostId, Long studyGroupId, String participantId, ParticipantUpdateRequest statusRequest) {
        StudyGroup studyGroup = getStudyGroup(studyGroupId);

        switch (statusRequest.status()) {
            case APPROVE -> studyGroup.approve(MemberId.of(hostId), MemberId.of(participantId));
            case REJECT -> studyGroup.reject(MemberId.of(hostId), MemberId.of(participantId));
            case KICK -> studyGroup.kick(MemberId.of(hostId), MemberId.of(participantId));
        }
    }

    private StudyGroup getStudyGroup(Long studyGroupId) {
        return studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new DataNotFoundException(studyGroupId + " 스터디그룹을 찾을 수 없습니다."));
    }
}
