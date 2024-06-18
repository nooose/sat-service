package com.sat.user.query

import com.fasterxml.jackson.annotation.JsonInclude
import com.sat.common.Cursor
import com.sat.user.command.domain.member.Member
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class MemberSimpleQuery(
    override val id: Long,
    val name: String,
    val email: String,
    val createdDateTime: LocalDateTime?,
) : Cursor {
    companion object {
        fun from(entity: Member): MemberSimpleQuery {
            return MemberSimpleQuery(
                id = entity.id,
                name = entity.name,
                email = entity.email,
                createdDateTime = entity.createdDateTime,
            )
        }
    }
}
