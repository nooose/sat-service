package com.sat.board.ui.web

import com.sat.board.application.ArticleCommandService
import com.sat.board.application.ArticleQueryService
import com.sat.board.application.dto.command.ArticleCreateCommand
import com.sat.board.application.dto.command.ArticleUpdateCommand
import com.sat.board.application.dto.query.ArticleQuery
import com.sat.board.domain.dto.ArticleWithCount
import com.sat.common.config.security.AuthenticatedMember
import com.sat.common.config.security.LoginMember
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

private val log = KotlinLogging.logger {}

@RestController
class ArticleRestController(
    private val articleCommandService: ArticleCommandService,
    private val articleQueryService: ArticleQueryService,
) {

    @PostMapping("/board/articles")
    fun create(@RequestBody @Valid command: ArticleCreateCommand): ResponseEntity<Unit> {
        val articleId = articleCommandService.create(command)
        return ResponseEntity.created(URI.create("/board/articles/$articleId")).build()
    }

    @GetMapping("/board/articles/{articleId}")
    fun article(
        @PathVariable articleId: Long,
        @LoginMember member: AuthenticatedMember,
    ): ArticleQuery {
        return articleQueryService.get(articleId, member.id)
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
