package com.sat.user.query

import com.sat.common.Cursor
import com.sat.user.command.domain.point.PointType
import java.time.LocalDateTime

class MyPointQuery(
    override val id: Long,
    val point: Int,
    type: PointType,
    val createdDateTime: LocalDateTime,
) : Cursor {
    val type = type.title
}
