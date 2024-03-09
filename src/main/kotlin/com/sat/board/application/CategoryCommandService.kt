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
        val isDuplicated = categoryRepository.existsByNameIs(command.name!!)
        if (isDuplicated) {
            throw DuplicateException("카테고리 이름이 존재합니다. - ${command.name}")
        }

        if (command.parentId != null) {
            exists(command.parentId)
        }

        val category = Category(CategoryName(command.name), command.parentId)
        categoryRepository.save(category)
    }

    fun update(id: Long, command: CategoryUpdateCommand) {
        val category = getCategory(id)
        if (command.parentId != null) {
            exists(command.parentId)
        }

        val categoryDto = CategoryDto(CategoryName(command.name), parentId = command.parentId)
        category.update(categoryDto)
    }

    fun delete(id: Long) {
        val category = getCategory(id)
        val exists = categoryRepository.existsByParentIdIs(id)
        if (exists) {
            throw ChildExistsException("자식이 존재하는 카테고리입니다. - $id")
        }
        categoryRepository.delete(category)
    }

    private fun getCategory(id: Long) = categoryRepository.findByIdOrThrow(id) { "카테고리를 찾을 수 없습니다. - ${id}" }
    private fun exists(id: Long) {
        val exists = categoryRepository.existsById(id)
        require(exists) { "존재하지 않는 ID 입니다. -${id}" }
    }
}
