package com.sat.user.query

data class PointQuery(
    val memberId: Long,
    val point: Double,
    var memberName: String? = null,
)
