package com.sat.study.application;

import com.sat.common.exception.DataNotFoundException;
import com.sat.member.domain.MemberId;
import com.sat.study.application.dto.ParticipantUpdateRequest;
import com.sat.study.application.dto.StudyGroupCreateRequest;
import com.sat.study.application.dto.StudyGroupResponse;
import com.sat.study.domain.StudyGroup;
import com.sat.study.domain.StudyGroupRepository;
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
        StudyGroup studyGroup = request.toEntity(MemberId.of(hostId));
        studyGroupRepository.save(studyGroup);
        return StudyGroupResponse.from(studyGroup);
    }

    public List<StudyGroupResponse> findAll() {
        return studyGroupRepository.findAll()
                .stream()
                .map(StudyGroupResponse::from)
                .toList();
    }

    @Transactional
    public void requestJoin(long studyGroupId, String participantId) {
        StudyGroup studyGroup = getStudyGroup(studyGroupId);
        studyGroup.requestJoin(MemberId.of(participantId));
    }

    @Transactional
    public void updateStatus(String hostId, long studyGroupId, String participantId, ParticipantUpdateRequest statusRequest) {
        StudyGroup studyGroup = getStudyGroup(studyGroupId);

        switch (statusRequest.status()) {
            case APPROVE -> studyGroup.approve(MemberId.of(hostId), MemberId.of(participantId));
            case REJECT -> studyGroup.reject(MemberId.of(hostId), MemberId.of(participantId));
            case KICK -> studyGroup.kick(MemberId.of(hostId), MemberId.of(participantId));
        }
    }

    private StudyGroup getStudyGroup(long studyGroupId) {
        return studyGroupRepository.findById(studyGroupId)
                .orElseThrow(() -> new DataNotFoundException("스터디그룹을 찾을 수 없습니다. - " + studyGroupId));
    }
}
