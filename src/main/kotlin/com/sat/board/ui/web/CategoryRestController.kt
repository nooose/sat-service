package com.sat.board.ui.web

import com.sat.board.command.application.CategoryCommandService
import com.sat.board.command.application.CategoryCreateCommand
import com.sat.board.command.application.CategoryUpdateCommand
import com.sat.board.query.CategoryQuery
import com.sat.board.query.CategoryQueryService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
class CategoryRestController(
    private val categoryCommandService : CategoryCommandService,
    private val categoryQueryService: CategoryQueryService,
) {

    @PostMapping("/board/categories")
    fun create(@RequestBody @Valid command: CategoryCreateCommand) {
        categoryCommandService.create(command)
    }

    @GetMapping("/board/categories")
    fun get(@RequestParam flatten: Boolean? = false): List<CategoryQuery> {
        return categoryQueryService.get(flatten)
    }

    @GetMapping("/board/categories/{id}")
    fun get(@PathVariable id: Long): CategoryQuery {
        return categoryQueryService.get(id)
    }

    @PutMapping("/board/categories/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid command: CategoryUpdateCommand,
    ) {
        categoryCommandService.update(id, command)
    }

    @DeleteMapping("/board/categories/{id}")
    fun delete(@PathVariable id: Long) {
        categoryCommandService.delete(id)
    }
}
