package com.sat.board.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll

@DisplayName("도메인 - 카테고리 이름")
class CategoryNameTest : StringSpec({

    "유효한 이름이 아니면 카테고리를 생성할 수 없다." {
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
            shouldThrow<IllegalArgumentException> { CategoryName(name) }
                .apply { println(this.message) }
        }
    }
})
