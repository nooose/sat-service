package com.sat.board.ui.web

import com.sat.board.application.CommentCommandService
import com.sat.board.application.CommentQueryService
import com.sat.board.application.dto.command.CommentCreateCommand
import com.sat.board.application.dto.query.CommentQuery
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName

@RestController
class CommentRestController(
    private val commentCommandService: CommentCommandService,
    private val commentQueryService: CommentQueryService,
) {

    @PostMapping("/board/articles/{articleId}/comments")
    fun create(
        @PathVariable articleId: Long,
        @RequestBody command: CommentCreateCommand,
    ): ResponseEntity<Unit> {
        commentCommandService.create(articleId, command)
        val uri = fromMethodName(ArticleRestController::class.java, "article", articleId)
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/board/articles/{articleId}/comments")
    fun get(@PathVariable articleId: Long): List<CommentQuery>{
        return commentQueryService.get(articleId)
    }
}
