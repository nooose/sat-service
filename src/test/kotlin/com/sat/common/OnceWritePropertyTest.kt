package com.sat.common

import io.kotest.assertions.throwables.shouldThrowUnit
import io.kotest.core.spec.DisplayName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty

internal data class Book(
    val id: Int,
) {
    var name: String by OnceWriteProperty("")
}


@DisplayName("학습 - 위임 프로퍼티")
class OnceWritePropertyTest : DescribeSpec({

    describe("위임 프로퍼티 테스트") {
        val book = Book(1)

        it("초기화 시 기본값을 세팅할 수 있다.") {
            book.name.shouldBeEmpty()
        }

        context("처음 초기값을 할당하면.") {
            book.name = "테스트"
            it("할당된다") {
                book.name shouldBe "테스트"
            }
        }

        context("한번 더 할당하면") {
            it("예외를 던진다.") {
                shouldThrowUnit<IllegalArgumentException> {
                    book.name = "두번째"
                }.apply { println(message) }
            }
        }
    }
})
