package com.sat.study.domain.port

import com.sat.study.domain.StudyGroup
import org.springframework.data.jpa.repository.JpaRepository

interface StudyGroupRepository : JpaRepository<StudyGroup, Long>
