package com.sat.board.ui.web

import com.sat.board.command.application.CommentCommandService
import com.sat.board.command.application.CommentCreateCommand
import com.sat.board.command.application.CommentUpdateCommand
import com.sat.board.query.CommentQuery
import com.sat.board.query.CommentQueryService
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri

@RestController
class CommentRestController(
    private val commentCommandService: CommentCommandService,
    private val commentQueryService: CommentQueryService,
) {

    @PostMapping("/board/articles/{articleId}/comments")
    fun create(
        @PathVariable articleId: Long,
        @RequestBody @Valid command: CommentCreateCommand,
    ): ResponseEntity<Unit> {
        commentCommandService.create(articleId, command)
        val uri = fromCurrentRequestUri()
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/board/articles/{articleId}/comments")
    fun get(@PathVariable articleId: Long): List<CommentQuery>{
        return commentQueryService.get(articleId)
    }

    @PutMapping("/board/comments/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid command: CommentUpdateCommand,
        @LoginMember loginMember: AuthenticatedMember,
    ) {
        commentCommandService.update(id, command, loginMember.id)
    }

    @DeleteMapping("/board/comments/{id}")
    fun delete(
        @PathVariable id: Long,
        @LoginMember loginMember: AuthenticatedMember,
    ) {
        commentCommandService.delete(id, loginMember.id)
    }
}
