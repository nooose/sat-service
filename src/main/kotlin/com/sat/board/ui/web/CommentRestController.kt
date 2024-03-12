package com.sat.board.ui.web

import com.sat.board.application.CommentCommandService
import com.sat.board.application.dto.command.CommentCreateCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodName

@RestController
class CommentRestController(
    private val commentCommandService: CommentCommandService,
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
}
