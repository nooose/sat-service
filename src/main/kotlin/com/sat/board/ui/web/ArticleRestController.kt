package com.sat.board.ui.web

import com.sat.board.application.ArticleCommandService
import com.sat.board.application.dto.command.ArticleCreateCommand
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleRestController(
    private val articleCommandService: ArticleCommandService,
) {

    @PostMapping("/board/articles")
    fun create(@RequestBody command: ArticleCreateCommand) {
        articleCommandService.create(command)
    }
}
