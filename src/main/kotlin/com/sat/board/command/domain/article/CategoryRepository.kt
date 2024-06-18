package com.sat.board.command.domain.article

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByName(name: CategoryName): Boolean

    fun existsByParentIdIs(id: Long): Boolean
}
