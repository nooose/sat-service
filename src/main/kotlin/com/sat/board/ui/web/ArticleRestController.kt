package com.sat.board.ui.web

import com.sat.board.application.command.ArticleCommandService
import com.sat.board.application.command.dto.ArticleCreateCommand
import com.sat.board.application.command.dto.ArticleUpdateCommand
import com.sat.board.application.query.ArticleQueryService
import com.sat.board.application.query.dto.ArticleQuery
import com.sat.board.domain.dto.query.ArticleWithCount
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

private val log = KotlinLogging.logger {}

@RestController
class ArticleRestController(
    private val articleCommandService: ArticleCommandService,
    private val articleQueryService: ArticleQueryService,
) {

    @PostMapping("/board/articles")
    fun create(@RequestBody @Valid command: ArticleCreateCommand): ResponseEntity<Unit> {
        val articleId = articleCommandService.create(command)
        val uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/board/articles/$articleId")
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/board/articles/{articleId}")
    fun article(
        @PathVariable articleId: Long,
        @LoginMember member: Any,
    ): ArticleQuery {
        return when (member) {
            is AuthenticatedMember -> articleQueryService.get(articleId, member.id)
            else -> articleQueryService.get(articleId)
        }
    }

    @PostMapping("/board/articles/{articleId}:like")
    fun likeArticle(
        @PathVariable articleId: Long,
        @LoginMember member: AuthenticatedMember,
    ) {
        articleCommandService.like(articleId, member.id)
    }

    @GetMapping("/board/articles")
    fun articles(): List<ArticleWithCount> {
        return articleQueryService.get()
    }

    @PutMapping("/board/articles/{articleId}")
    fun update(
        @PathVariable articleId: Long,
        @RequestBody @Valid command: ArticleUpdateCommand,
    ) {
        articleCommandService.update(articleId, command)
    }

    @DeleteMapping("/board/articles/{articleId}")
    fun delete(@PathVariable articleId: Long) {
        return articleCommandService.delete(articleId)
    }
}
