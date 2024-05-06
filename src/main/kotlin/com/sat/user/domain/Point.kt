package com.sat.user.domain

import jakarta.persistence.*


@Entity
class Point(
    val memberId: Long,
    @Enumerated(EnumType.STRING)
    val type: PointType,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    val point = type.score

    companion object {
        fun login(memberId: Long): Point {
            return Point(memberId, PointType.LOGIN)
        }

        fun comment(memberId: Long): Point {
            return Point(memberId, PointType.COMMENT)
        }

        fun article(memberId: Long): Point {
            return Point(memberId, PointType.ARTICLE)
        }
    }
}
