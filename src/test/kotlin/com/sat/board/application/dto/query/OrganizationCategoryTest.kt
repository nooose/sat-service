package com.sat.board.application.dto.query

import com.sat.board.domain.Category
import com.sat.board.domain.CategoryName
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

@DisplayName("도메인 - 카테고리 계층 테스트")
class OrganizationCategoryTest : BehaviorSpec({
    Given("부모 카테고리와 자식카테고리가 섞인 카테고리 리스트가 있고") {
        val categories = listOf(
            Category(CategoryName("컴퓨터"), null, 1L),
            Category(CategoryName("애플"), 1L, 2L),
            Category(CategoryName("삼성"), 1L, 3L),
            Category(CategoryName("맥북프로"), 2L, 5L),
            Category(CategoryName("맥북에어"), 2L, 6L),
            Category(CategoryName("갤럭시북"), 3L, 7L),
        )
        When("계층을 만들면") {
            val organizationCategories = OrganizationCategory(categories)
            Then("부모하위에 자식 카테고리가 존재한다.") {
                organizationCategories.categories shouldBe listOf(
                    CategoryQuery(
                        1L, "컴퓨터",
                        mutableListOf(
                            CategoryQuery(
                                2L, "애플", parentId = 1L,
                                children = mutableListOf(
                                    CategoryQuery(5L, "맥북프로", parentId = 2L),
                                    CategoryQuery(6L, "맥북에어", parentId = 2L),
                                )
                            ),
                            CategoryQuery(
                                3L, "삼성", parentId = 1L,
                                children = mutableListOf(
                                    CategoryQuery(7L, "갤럭시북", parentId = 3L),
                                )
                            ),
                        )
                    )
                )
            }
        }
    }
})
