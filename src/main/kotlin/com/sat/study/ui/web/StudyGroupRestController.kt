package com.sat.study.ui.web

import com.sat.study.application.StudyGroupCommandService
import com.sat.study.application.dto.command.StudyGroupCreateCommand
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
}
