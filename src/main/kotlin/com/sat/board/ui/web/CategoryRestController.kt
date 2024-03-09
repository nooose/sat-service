package com.sat.board.ui.web

import com.sat.board.application.CategoryCommandService
import com.sat.board.application.CategoryQueryService
import com.sat.board.application.dto.command.CategoryCreateCommand
import com.sat.board.application.dto.command.CategoryUpdateCommand
import com.sat.board.application.dto.query.CategoryQuery
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName

@RestController
class CategoryRestController(
    private val categoryCommandService : CategoryCommandService,
    private val categoryQueryService: CategoryQueryService,
) {

    @PostMapping("/board/categories")
    fun create(@RequestBody @Valid command: CategoryCreateCommand): ResponseEntity<Unit> {
        categoryCommandService.create(command)
        val uri = fromMethodName(CategoryRestController::class.java, "get")
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/board/categories")
    fun get(): List<CategoryQuery> {
        return categoryQueryService.get()
    }

    @PutMapping("/board/category/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid command: CategoryUpdateCommand,
    ) {
        categoryCommandService.update(id, command)
    }
    @DeleteMapping("/board/category/{id}")
    fun delete(@PathVariable id: Long) {
        categoryCommandService.delete(id)
    }
}
