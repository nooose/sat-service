package com.sat.board.domain.event

import com.sat.user.domain.PointType

class CommentCreateEvent(
    val articleId: Long,
    val memberId: Long,
    val pointType: PointType = PointType.COMMENT,
) {
}
