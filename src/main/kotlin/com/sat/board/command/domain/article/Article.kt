package com.sat.board.command.domain.article

import com.sat.common.BaseEntity
import jakarta.persistence.*

@Entity
class Article(
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false)
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id", nullable = false)
    var category: Category,
    id: Long = 0L,
) : BaseEntity(id) {
    var views: Long = 0
    var isDeleted: Boolean = false

    fun update(that: ArticleUpdateDto) {
        this.title = that.title
        this.content = that.content
    }

    fun delete() {
        isDeleted = true
    }
}
