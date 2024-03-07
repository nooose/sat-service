package com.sat.board.ui.web

import com.sat.board.application.CategoryCommandService
import com.sat.board.application.CategoryQueryService
import com.sat.board.application.dto.command.CategoryCreateCommand
import com.sat.board.application.dto.query.CategoryQuery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName

@RestController
class CategoryRestController(
    private val categoryCommandService : CategoryCommandService,
    private val categoryQueryService: CategoryQueryService,
) {

    @PostMapping("/board/categories")
    fun create(@RequestBody command: CategoryCreateCommand): ResponseEntity<Unit> {
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
}
