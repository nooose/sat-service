package com.sat.board.application

import com.sat.board.application.dto.command.CategoryCreateCommand
import com.sat.board.domain.Category
import com.sat.board.domain.port.CategoryRepository
import com.sat.common.domain.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class CategoryCommandService(
    private val categoryRepository: CategoryRepository,
) {

    fun create(command: CategoryCreateCommand) {
        val isDuplicated = categoryRepository.existsByNameIs(command.name)
        if (isDuplicated) {
            throw NotFoundException("카테고리 이름이 존재합니다. - ${command.name}")
        }

        if (command.parentId != null) {
            val exists = categoryRepository.existsById(command.parentId)
            require(exists) { "부모가 존재하지 않습니다. -${command.parentId}" }
        }

        val category = Category(command.name, command.parentId)
        categoryRepository.save(category)

    }
}
