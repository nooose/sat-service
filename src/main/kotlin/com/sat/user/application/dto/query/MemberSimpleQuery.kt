package com.sat.user.application.dto.query

import com.sat.user.domain.Member
import java.time.LocalDateTime

data class MemberSimpleQuery(
    val id: Long,
    val name: String,
    val email: String,
    val createdDateTime: LocalDateTime,
) {
    companion object {
        fun from(entity: Member): MemberSimpleQuery {
            return MemberSimpleQuery(
                id = entity.id!!,
                name = entity.name,
                email = entity.email,
                createdDateTime = entity.createdDateTime!!,
            )
        }
    }
}
