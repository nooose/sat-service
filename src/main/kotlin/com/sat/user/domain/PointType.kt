package com.sat.user.domain

enum class PointType(
    val score: Int,
) {
    LOGIN(10),
    ARTICLE(10),
    COMMENT(3),
    HART(3),
}
