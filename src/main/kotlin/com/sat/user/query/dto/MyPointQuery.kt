package com.sat.user.query.dto

import com.sat.user.command.domain.point.PointType
import java.time.LocalDateTime

class MyPointQuery(
    val id: Long,
    val point: Int,
    type: PointType,
    val createdDateTime: LocalDateTime,
) {
    val type = type.title
}
