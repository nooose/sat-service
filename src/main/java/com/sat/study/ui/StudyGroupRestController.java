package com.sat.study.ui;

import com.sat.study.application.StudyGroupService;
import com.sat.study.application.dto.ParticipantUpdateRequest;
import com.sat.study.application.dto.StudyGroupCreateRequest;
import com.sat.study.application.dto.StudyGroupResponse;
import com.sat.study.domain.type.StudyCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/v1")
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
                                           @RequestBody @Validated StudyGroupCreateRequest request) {
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

    @GetMapping("/studygroups/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = Arrays.stream(StudyCategory.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(categories);
    }
}
