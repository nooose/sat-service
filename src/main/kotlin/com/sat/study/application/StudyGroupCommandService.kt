package com.sat.study.application

import com.sat.study.application.dto.command.StudyGroupCreateCommand
import com.sat.study.domain.Participant
import com.sat.study.domain.port.StudyGroupRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class StudyGroupCommandService(
    private val repository: StudyGroupRepository,
) {

    fun create(hostId: Long, command: StudyGroupCreateCommand) {
        val studyGroup = command.toEntity(hostId)
        repository.save(studyGroup)
    }

    fun join(groupId: Long, participantId: Long) {
        val studyGroup = repository.findByIdOrNull(groupId) ?: throw IllegalArgumentException("스터디 그룹을 찾을 수 없습니다. - $groupId")
        studyGroup.add(Participant.candidate(participantId))
    }
}
