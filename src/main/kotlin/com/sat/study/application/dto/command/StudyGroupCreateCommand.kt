package com.sat.study.application.dto.command

import com.sat.study.domain.Period
import com.sat.study.domain.Status
import com.sat.study.domain.StudyGroup
import jakarta.validation.constraints.NotBlank

data class StudyGroupCreateCommand(
    @field:NotBlank
    private val _title: String?,
    @field:NotBlank
    private val _content: String?,
    @field:NotBlank
    private val _period: Period?,
) {

    val title: String
        get() = _title!!

    val content: String
        get() = _content!!

    val period: Period
        get() = _period!!

    fun toEntity(): StudyGroup {
        return StudyGroup(
            title = title,
            content = content,
            status = Status.RECRUITING,
            period = period,
        )
    }
}
