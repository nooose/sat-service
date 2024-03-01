package com.sat.study.application.dto.command

import jakarta.validation.constraints.NotNull

data class StudyGroupJoinCommand(
    @field:NotNull
    private val _memberId: Long?,
) {

    val memberId: Long
        get() = _memberId!!
}
