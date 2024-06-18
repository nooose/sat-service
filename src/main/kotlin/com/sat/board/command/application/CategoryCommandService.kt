package com.sat.board.command.application

import com.sat.board.command.domain.article.Category
import com.sat.board.command.domain.article.CategoryName
import com.sat.board.command.domain.article.CategoryRepository
import com.sat.board.command.domain.article.ChildExistsException
import com.sat.board.command.domain.article.CategoryDto
import com.sat.common.domain.exception.DuplicateException
import com.sat.common.utils.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CategoryCommandService(
    private val categoryRepository: CategoryRepository,
) {

    fun create(command: CategoryCreateCommand) {
        val name = CategoryName(command.name)
        validateName(name)
        validateParent(command.parentId)

        val category = Category(name, command.parentId)
        categoryRepository.save(category)
    }

    fun update(id: Long, command: CategoryUpdateCommand) {
        val name = CategoryName(command.name)
        validateName(name)
        validateParent(command.parentId)

        val category = getCategory(id)
        val categoryDto = CategoryDto(name, command.parentId)
        category.update(categoryDto)
    }

    private fun validateName(name: CategoryName) {
        val isDuplicated = categoryRepository.existsByName(name)
        if (isDuplicated) {
            throw DuplicateException("중복된 카테고리 이름입니다. - ${name.value}")
        }
    }

    private fun validateParent(parentId: Long?) {
        if (parentId != null) {
            val exists = categoryRepository.existsById(parentId)
            require(exists) { "상위 카테고리가 존재하지 않습니다. - $parentId" }
        }
    }

    fun delete(id: Long) {
        val category = getCategory(id)
        val exists = categoryRepository.existsByParentIdIs(id)
        if (exists) {
            throw ChildExistsException("하위 카테고리를 모두 삭제 후 삭제할 수 있습니다. - $id")
        }
        categoryRepository.delete(category)
    }

    private fun getCategory(id: Long) = categoryRepository.findByIdOrThrow(id) { "카테고리를 찾을 수 없습니다. - $id" }
}
