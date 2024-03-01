package com.sat.study.application

import com.sat.study.application.dto.command.StudyGroupCreateCommand
import com.sat.study.domain.port.StudyGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class StudyGroupCommandService(
    private val repository: StudyGroupRepository,
) {

    fun create(command: StudyGroupCreateCommand) {
        val studyGroup = command.toEntity()
        repository.save(studyGroup)
    }
}
