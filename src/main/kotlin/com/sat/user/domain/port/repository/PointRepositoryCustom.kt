package com.sat.user.domain.port.repository

import com.sat.common.domain.CursorRequest
import com.sat.common.domain.PageCursor
import com.sat.user.application.query.dto.MyPointQuery

interface PointRepositoryCustom {
    fun getTotalPoint(memberId: Long): Int

    fun findByCursor(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<MyPointQuery>>
}
