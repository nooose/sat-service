package com.sat.board.domain

import com.sat.board.domain.dto.CategoryDto
import com.sat.common.domain.AuditingFields
import jakarta.persistence.*

@Entity
class Category(
    @Embedded
    var name: CategoryName,
    var parentId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditingFields() {

    fun update(that: CategoryDto) {
        this.name = that.name
        this.parentId = that.parentId
    }
}
