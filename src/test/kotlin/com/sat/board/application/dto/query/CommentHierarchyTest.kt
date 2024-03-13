package com.sat.board.application.dto.query

import com.sat.board.domain.Article
import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import com.sat.board.domain.Comment
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

@DisplayName("도메인 - 댓글 계층 테스트")
class CommentHierarchyTest : BehaviorSpec( {
    Given("댓글리스트가 있고") {
        val comments = listOf(
            Comment(
                id = 1L,
                content = "재밌어요",
                article = Article(
                    id = 1L,
                    title = "테스트",
                    content = "테스트입니다",
                    category = Category(
                        id = 1L,
                        name = CategoryName("IT")
                    )
                )
            ),
            Comment(
                id = 2L,
                content = "재미없어요",
                article = Article(
                    id = 1L,
                    title = "테스트",
                    content = "테스트입니다",
                    category = Category(
                        id = 1L,
                        name = CategoryName("IT")
                    )
                )
            ),
            Comment(
                id = 3L,
                content = "재밌을뻔했어요",
                parentId = 1L,
                article = Article(
                    id = 1L,
                    title = "테스트",
                    content = "테스트입니다",
                    category = Category(
                        id = 1L,
                        name = CategoryName("IT")
                    )
                )
            ),
        )
        When("계층화 하면") {
            val commentHierarchy = CommentHierarchy(comments)
            Then("부모하위에 자식이 존재한다.") {
                commentHierarchy.comments shouldBe listOf(
                    CommentQuery(
                        articleId = 1L,
                        id = 1L,
                        content = "재밌어요",
                        children = mutableListOf(
                            CommentQuery(
                                articleId = 1L,
                                id = 3L,
                                content = "재밌을뻔했어요",
                                parentId = 1L,
                            )
                        )
                    ),
                    CommentQuery(
                        articleId = 1L,
                        id = 2L,
                        content = "재미없어요",
                    )
                )
            }
        }
    }
})
