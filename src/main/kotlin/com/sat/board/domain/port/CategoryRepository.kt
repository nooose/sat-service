package com.sat.board.domain.port

import com.sat.board.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByNameIs(name: String): Boolean

    fun existsByParentIdIs(id: Long): Boolean
}
