package com.sat.user.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

private const val INITIAL_POINT = 10

@Entity
class Point(
    val memberId: Long,
    var point: Int = INITIAL_POINT,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    fun loginAward() {
        point += 10
    }
}
