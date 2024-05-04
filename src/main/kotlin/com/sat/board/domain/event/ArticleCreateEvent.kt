package com.sat.board.domain.event

import com.sat.user.domain.PointType

class ArticleCreateEvent(
    val memberId: Long,
    val pointType: PointType = PointType.ARTICLE,
) {
}
