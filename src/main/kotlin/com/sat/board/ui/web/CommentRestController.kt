package com.sat.board.ui.web

import com.sat.board.application.CommentCommandService
import com.sat.board.application.CommentQueryService
import com.sat.board.application.dto.command.CommentCreateCommand
import com.sat.board.application.dto.command.CommentUpdateCommand
import com.sat.board.application.dto.query.CommentQuery
import com.sat.user.domain.LoginMemberInfo
import com.sat.user.ui.web.LoginMember
import jakarta.validation.Valid
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
        @RequestBody @Valid command: CommentCreateCommand,
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

    @PutMapping("/board/comments/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid command: CommentUpdateCommand,
        @LoginMember loginMember: LoginMemberInfo,
    ) {
        commentCommandService.update(id, command, loginMember.id)
    }

    @DeleteMapping("/board/comments/{id}")
    fun delete(
        @PathVariable id: Long,
        @LoginMember loginMember: LoginMemberInfo,
    ) {
        commentCommandService.delete(id, loginMember.id)
    }
}
