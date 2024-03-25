package com.sat.board.application.dto.query

import com.sat.board.domain.*
import com.sat.board.domain.dto.CommentDto
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe


@DisplayName("도메인 - 댓글 계층 테스트")
class CommentHierarchyTest : BehaviorSpec( {
    Given("댓글리스트가 있고") {
        val comments = listOf(
            CommentDto(
                memberId = 1L,
                memberName = "영록",
                id = 1L,
                content = "재밌어요",
            ),
            CommentDto(
                memberId = 2L,
                memberName = "준혁",
                id = 2L,
                content = "재미없어요",
            ),
            CommentDto(
                memberId = 3L,
                memberName = "규정",
                id = 3L,
                content = "재밌을뻔했어요",
                parentId = 1L,
            ),
        )
        When("계층화 하면") {
            val commentHierarchy = CommentHierarchy(comments)
            Then("부모하위에 자식이 존재한다.") {
                commentHierarchy.comments shouldBe listOf(
                    CommentQuery(
                        memberId = 1L,
                        memberName = "영록",
                        id = 1L,
                        content = "재밌어요",
                        children = mutableListOf(
                            CommentQuery(
                                memberId = 3L,
                                memberName = "규정",
                                id = 3L,
                                content = "재밌을뻔했어요",
                                parentId = 1L,
                            )
                        )
                    ),
                    CommentQuery(
                        memberId = 2L,
                        memberName = "준혁",
                        id = 2L,
                        content = "재미없어요",
                    )
                )
            }
        }
    }
})
