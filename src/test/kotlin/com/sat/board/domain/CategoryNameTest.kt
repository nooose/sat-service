package com.sat.board.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forAll

@DisplayName("도메인 - 카테고리 이름")
class CategoryNameTest : BehaviorSpec({
    Given("유효하지 않은 카테고리 이름이 주어지고") {
        listOf(
            "#$@컴퓨터",
            "123",
            " ",
            "",
            " 컴퓨터 ",
            "124컴퓨터",
            "0컴1퓨2터3",
            "0a1b2c3",
            "컴",
            "컴".repeat(9),
        ).forAll { name ->
            When("$name 카테고리 이름을 생성하면") {
                Then("예외가 발생한다") {
                    shouldThrow<IllegalArgumentException> { CategoryName(name) }.apply {
                        println(this.message)
                    }
                }
            }
        }
    }

})
