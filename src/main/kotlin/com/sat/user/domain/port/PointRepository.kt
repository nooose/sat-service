package com.sat.user.domain.port

import com.sat.user.domain.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<Point, Long> {

    fun findByMemberId(memberId: Long): Point?
}
