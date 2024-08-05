package com.sat.board.command.domain.article

import com.sat.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Article(
    var title: String,
    var content: String,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "category_id")
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
