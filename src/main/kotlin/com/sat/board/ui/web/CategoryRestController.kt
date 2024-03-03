package com.sat.board.ui.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryRestController {

    @PostMapping("/board/categories")
    fun create() {
        TODO("카테고리 생성")
    }

    @GetMapping("/board/categories")
    fun get() {
        TODO("카테고리 계층 조회")
    }
}
