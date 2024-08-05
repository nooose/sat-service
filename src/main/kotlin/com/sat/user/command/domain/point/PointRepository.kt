package com.sat.user.command.domain.point

import org.springframework.data.jpa.repository.JpaRepository

interface PointRepository : JpaRepository<Point, Long>
