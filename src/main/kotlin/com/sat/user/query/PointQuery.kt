package com.sat.user.query

data class PointQuery(
    val memberId: Long,
    val point: Int,
    var memberName: String? = null,
)
