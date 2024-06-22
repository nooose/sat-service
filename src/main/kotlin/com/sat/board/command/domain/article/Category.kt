package com.sat.board.command.domain.article

import com.sat.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity

@Entity
class Category(
    @Embedded
    var name: CategoryName,
    var parentId: Long? = null,
    id: Long = 0L,
) : BaseEntity(id) {

    fun update(that: CategoryUpdateDto) {
        this.name = that.name
        this.parentId = that.parentId
    }
}
