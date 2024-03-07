package com.sat.board.ui.web

import com.sat.board.application.CategoryCommandService
import com.sat.board.application.CategoryQueryService
import com.sat.board.application.dto.command.CategoryCreateCommand
import com.sat.board.application.dto.query.CategoryQuery
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryRestController(
    private val categoryCommandService : CategoryCommandService,
    private val categoryQueryService: CategoryQueryService,
) {

    @PostMapping("/board/categories")
    fun create(@RequestBody command: CategoryCreateCommand): ResponseEntity<Unit> {
        categoryCommandService.create(command)
        // todo: 메인으로 가는 api 가 만들어지면 게시글 생성처럼 main 으로 보내는 uri 담아서 보내기
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/board/categories")
    fun get(): List<CategoryQuery> {
        return categoryQueryService.get()
    }
}
