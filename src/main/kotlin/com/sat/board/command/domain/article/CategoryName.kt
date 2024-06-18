package com.sat.board.command.domain.article

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class CategoryName(
    @Column(name = "name")
    val value: String,
) {
    init {
        require(value.length in MIN..MAX) { "카테고리 이름은 ${MIN}글자이상 ${MAX}글자 이하로 작성해주세요. - $value" }
        require(DEFAULT_PATTERN.matches(value)) { "카테고리 이름은 한글 또는 영문만 가능합니다. - $value" }
        require(value.trim() == value) { "카테고리 이름 앞뒤에 공백을 넣을 수 없습니다." }
    }

    companion object {
        private val DEFAULT_PATTERN = Regex("^[a-zA-Z가-힣]+\$")
        private const val MIN = 2
        private const val MAX = 8
    }
}
