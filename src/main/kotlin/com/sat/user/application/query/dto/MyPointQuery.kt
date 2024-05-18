package com.sat.user.application.query.dto

import com.sat.user.domain.Point
import java.time.LocalDateTime

data class MyPointQuery(
    val id: Long,
    val point: Int,
    val type: String,
    val createdDateTime: LocalDateTime,
) {

    companion object {
        fun from(entity: Point): MyPointQuery {
            return MyPointQuery(
                id = entity.id!!,
                point = entity.point,
                type = entity.type.title,
                createdDateTime = entity.createdDateTime!!
            )
        }
    }
}
