package com.sat.study.application.dto.command

import com.fasterxml.jackson.annotation.JsonProperty
import com.sat.study.domain.Participant
import com.sat.study.domain.Period
import com.sat.study.domain.StudyGroup
import jakarta.validation.constraints.NotBlank

data class StudyGroupCreateCommand(
    @JsonProperty("title")
    @field:NotBlank
    private val _title: String?,
    @JsonProperty("content")
    @field:NotBlank
    private val _content: String?,
    @JsonProperty("period")
    @field:NotBlank
    private val _period: Period?,
) {

    val title: String
        get() = _title!!

    val content: String
        get() = _content!!

    val period: Period
        get() = _period!!

    fun toEntity(hostId: Long): StudyGroup {
        return StudyGroup.open(
            title = title,
            content = content,
            period = period,
            Participant.host(hostId)
        )
    }
}
