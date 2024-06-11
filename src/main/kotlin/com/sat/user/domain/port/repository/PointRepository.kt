package com.sat.user.domain.port.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.sat.user.domain.Point
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<Point, Long>, KotlinJdslJpqlExecutor {

    fun findAllByMemberIdOrderByIdDesc(memberId: Long): List<Point>
}
