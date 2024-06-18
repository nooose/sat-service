package com.sat.user.command.domain.point

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<Point, Long>, KotlinJdslJpqlExecutor {

    fun findAllByMemberIdOrderByIdDesc(memberId: Long): List<Point>
}
