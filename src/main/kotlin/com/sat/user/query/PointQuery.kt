package com.sat.user.query

data class PointQuery(
    //todo: member nickName 도 가져와서 넣어보기
    val memberId: Long,
    val point: Double,
)
