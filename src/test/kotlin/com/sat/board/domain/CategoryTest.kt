package com.sat.board.domain

import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

@DisplayName(name = "도메인 - 카테고리 테스트")
class CategoryTest : BehaviorSpec({
    Given("카테고리 부모 ID가 주어지고") {
        val parentId = 10L
        When("부모가 있는 카테고리를 만들면") {
            val category = Category("컴퓨터", parentId = parentId)
            Then("해당 카테고리는 Root가 아니다") {
                category.isRoot() shouldBe false
            }
        }
    }
})
