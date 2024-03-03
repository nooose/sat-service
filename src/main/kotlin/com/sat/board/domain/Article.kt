package com.sat.board.domain

import com.sat.board.domain.dto.ArticleDto
import com.sat.common.domain.AuditingFields
import jakarta.persistence.*

@Entity
class Article(
    var title: String,
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id")
    var category: Category,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditingFields() {
    private var isDeleted: Boolean = false

    fun update(that: ArticleDto) {
        this.title = that.title
        this.content = that.content
        this.category = that.category
    }

    fun delete() {
        isDeleted = true
    }
}
