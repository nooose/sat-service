package com.sat.board.query

import com.sat.board.command.domain.article.CategoryRepository
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CategoryQueryService(
    private val categoryRepository: CategoryRepository,
) {
    // TODO: 캐싱 처리
    fun get(flatten: Boolean? = false): List<CategoryQuery> {
        val categories = categoryRepository.findAll()

        if (flatten == true) {
            return categories.map { CategoryQuery(
                id = it.id,
                name = it.name.value,
                parentId = it.parentId
            ) }
        }

        val hierarchy = CategoryHierarchy(categories)
        return hierarchy.categories
    }

    fun get(id: Long): CategoryQuery {
        val category = categoryRepository.findByIdOrThrow(id) { "카테고리를 찾을 수 없습니다. - $id" }
        return CategoryQuery.from(category)
    }
}
