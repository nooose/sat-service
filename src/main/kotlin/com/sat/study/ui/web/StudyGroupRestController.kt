package com.sat.study.ui.web

import com.sat.study.application.StudyGroupCommandService
import com.sat.study.application.dto.command.StudyGroupCreateCommand
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class StudyGroupRestController(
    private val studyGroupCommandService: StudyGroupCommandService,
) {

    @PostMapping("/study/groups")
    fun create(@RequestBody command: StudyGroupCreateCommand) {
        val hostId = Random.nextInt(1, 100_000).toLong() // TODO: 사용자의 ID
        studyGroupCommandService.create(hostId, command)
    }

    @PostMapping("/study/groups/{groupId}/participants:request")
    fun join(
        @PathVariable groupId: Long,
    ) {
        val participantId = Random.nextInt(1, 100_000).toLong() // TODO: 사용자의 ID
        studyGroupCommandService.join(groupId, participantId)
    }
}
