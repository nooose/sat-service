package com.sat.study.ui.web

import com.sat.study.application.StudyGroupCommandService
import com.sat.study.application.dto.command.StudyGroupCreateCommand
import com.sat.study.application.dto.command.StudyGroupJoinCommand
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StudyGroupRestController(
    private val studyGroupCommandService: StudyGroupCommandService,
) {

    @PostMapping("/study/groups")
    fun create(@RequestBody command: StudyGroupCreateCommand) {
        studyGroupCommandService.create(command)
    }

    @PostMapping("/study/groups/{groupId}/participants")
    fun join(
        @PathVariable groupId: Long,
        @RequestBody command: StudyGroupJoinCommand,
    ) {
        studyGroupCommandService.join(groupId, command)
    }
}
