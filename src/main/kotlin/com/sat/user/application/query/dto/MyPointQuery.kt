package com.sat.user.application.query.dto

import com.sat.user.domain.PointType
import java.time.LocalDateTime

class MyPointQuery(
    val id: Long,
    val point: Int,
    type: PointType,
    val createdDateTime: LocalDateTime,
) {
    val type = type.title
}
