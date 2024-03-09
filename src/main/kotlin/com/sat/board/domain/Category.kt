package com.sat.board.domain

import com.sat.board.domain.dto.CategoryDto
import jakarta.persistence.*

@Entity
class Category(
    @Embedded
    var name: CategoryName,
    var parentId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    fun update(that: CategoryDto) {
        this.name = that.name
        this.parentId = that.parentId
    }
}

@Embeddable
data class CategoryName(
    @Column(name = "name")
    val value: String,
) {
    init {
        require(value.length in MIN..MAX) { "카테고리 이름은 ${MIN}글자이상 ${MAX}글자 이하로 작성해주세요. - $value" }
        require(DEFAULT_PATTERN.matches(value)) { "카테고리 이름은 한글 또는 영문만 가능합니다. - $value" }
        require(value.trim() == value) { "카테고리 이름 앞뒤에 공백이 존재합니다." }
    }

    companion object {
        private val DEFAULT_PATTERN = Regex("^[a-zA-Z가-힣]+\$")
        private const val MIN = 2
        private const val MAX = 8
    }
}
