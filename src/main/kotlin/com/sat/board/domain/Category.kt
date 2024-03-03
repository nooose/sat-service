package com.sat.board.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Category(
    var name: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val parentId: Long? = null,
) {

    fun isRoot(): Boolean {
        return parentId == null
    }

    companion object {
        fun childOf(parentId: Long, name: String): Category {
            return Category(name = name, parentId = parentId)
        }
    }
}
