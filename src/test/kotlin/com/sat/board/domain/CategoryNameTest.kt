package com.sat.board.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldNotBe

@DisplayName("도메인 - 카테고리 이름")
class CategoryNameTest : BehaviorSpec({
    Given("유효하지 않은 카테고리 이름이 주어지고") {
        forAll(
            row("#$@컴퓨터"),
            row("123"),
            row(" "),
            row(""),
            row(" 컴퓨터 "),
            row("124컴퓨터"),
            row("0컴1퓨2터3"),
            row("0a1b2c3"),
            row("컴"),
            row("컴".repeat(9)),
        ) { name ->
            When("$name 카테고리 이름을 생성하면") {
                val exception = shouldThrow<IllegalArgumentException> {
                    CategoryName(name)
                }
                Then("예외가 발생한다") {
                    exception.message shouldNotBe null
                    println(exception.message)
                }
            }
        }
    }

})
