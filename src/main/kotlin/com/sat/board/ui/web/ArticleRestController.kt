package com.sat.board.ui.web

import com.sat.board.application.ArticleCommandService
import com.sat.board.application.ArticleQueryService
import com.sat.board.application.dto.command.ArticleCreateCommand
import com.sat.board.application.dto.query.ArticleQuery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName


@RestController
class ArticleRestController(
    private val articleCommandService: ArticleCommandService,
    private val articleQueryService: ArticleQueryService,
) {

    @PostMapping("/board/articles")
    fun create(@RequestBody command: ArticleCreateCommand): ResponseEntity<Unit> {
        val articleId = articleCommandService.create(command)
        val uri = fromMethodName(ArticleRestController::class.java, "article", articleId)
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/board/articles/{articleId}")
    fun article(@PathVariable articleId: Long): ArticleQuery {
        return articleQueryService.get(articleId)
    }

    @GetMapping("/board/articles")
    fun articles(): List<ArticleQuery> {
        return articleQueryService.get()
    }
}
