package com.sat.board.command.domain.article

import com.sat.common.AuditingFields
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

    fun update(that: ArticleUpdateDto) {
        this.title = that.title
        this.content = that.content
    }

    fun delete() {
        isDeleted = true
    }
}
