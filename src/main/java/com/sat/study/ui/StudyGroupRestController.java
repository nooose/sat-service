package com.sat.study.ui;

import com.sat.study.application.StudyGroupService;
import com.sat.study.application.dto.ParticipantUpdateRequest;
import com.sat.study.application.dto.StudyGroupCreateRequest;
import com.sat.study.application.dto.StudyGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudyGroupRestController {

    private final StudyGroupService studyGroupService;

    @GetMapping("/studygroups")
    public ResponseEntity<List<StudyGroupResponse>> findAll() {
        var studyGroups = studyGroupService.findAll();
        return ResponseEntity.ok().body(studyGroups);
    }

    @PostMapping("/studygroups")
    public ResponseEntity<Void> createStudyGroup(@AuthenticationPrincipal String principal,
                                           @RequestBody StudyGroupCreateRequest request) {
        StudyGroupResponse response = studyGroupService.create(principal, request);
        return ResponseEntity.created(URI.create("/studygroups/" + response.id())).build();
    }

    @PostMapping("/studygroups/{studyGroupId}/participants")
    public ResponseEntity<Void> requestJoin(@AuthenticationPrincipal String principal, @PathVariable Long studyGroupId) {
        studyGroupService.requestJoin(studyGroupId, principal);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/studygroups/{studyGroupId}/participants/{participantId}")
    public ResponseEntity<Void> requestJoin(@AuthenticationPrincipal String principal,
                                            @PathVariable Long studyGroupId,
                                            @PathVariable String participantId,
                                            @RequestBody ParticipantUpdateRequest participantStatusRequest) {
        studyGroupService.updateStatus(principal, studyGroupId, participantId, participantStatusRequest);
        return ResponseEntity.ok().build();
    }
}
