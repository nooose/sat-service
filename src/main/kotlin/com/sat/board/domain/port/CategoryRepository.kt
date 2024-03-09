package com.sat.board.domain.port

import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByName(name: CategoryName): Boolean

    fun existsByParentIdIs(id: Long): Boolean
}
