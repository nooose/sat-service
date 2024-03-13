package com.sat.board.application

import com.sat.board.application.dto.query.CategoryHierarchy
import com.sat.board.application.dto.query.CategoryQuery
import com.sat.board.domain.port.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CategoryQueryService(
    private val categoryRepository: CategoryRepository,
) {
    fun get(): List<CategoryQuery> {
        val categories = categoryRepository.findAll()
        val hierarchy = CategoryHierarchy(categories)
        return hierarchy.categories
    }
}
