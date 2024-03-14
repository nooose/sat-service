package com.sat.board.application

import com.sat.board.application.dto.command.CategoryCreateCommand
import com.sat.board.application.dto.command.CategoryUpdateCommand
import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import com.sat.board.domain.dto.CategoryDto
import com.sat.board.domain.exception.ChildExistsException
import com.sat.board.domain.port.CategoryRepository
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
            throw DuplicateException("카테고리 이름이 존재합니다. - ${name.value}")
        }
    }

    private fun validateParent(parentId: Long?) {
        if (parentId != null) {
            val exists = categoryRepository.existsById(parentId)
            require(exists) { "존재하지 않는 ID 입니다. - $parentId" }
        }
    }

    fun delete(id: Long) {
        val category = getCategory(id)
        val exists = categoryRepository.existsByParentIdIs(id)
        require(!exists) { throw ChildExistsException("자식이 존재하는 카테고리입니다. - $id") }
        categoryRepository.delete(category)
    }

    private fun getCategory(id: Long) = categoryRepository.findByIdOrThrow(id) { "카테고리를 찾을 수 없습니다. - $id" }
}
