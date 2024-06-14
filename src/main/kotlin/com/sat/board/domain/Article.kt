package com.sat.board.domain

import com.sat.board.domain.dto.ArticleWithoutCategoryDto
import com.sat.common.domain.AuditingFields
import jakarta.persistence.*

@Entity
class Article(
    var title: String,
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id")
    var category: Category,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : AuditingFields() {
    var isDeleted: Boolean = false

    fun update(that: ArticleWithoutCategoryDto) {
        this.title = that.title
        this.content = that.content
    }

    fun delete() {
        isDeleted = true
    }
}
