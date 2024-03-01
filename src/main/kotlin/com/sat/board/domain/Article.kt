package com.sat.board.domain

import jakarta.persistence.*

@Entity
class Article(
    var title: String,
    var content: String,
    @ManyToOne @JoinColumn(name = "category_id")
    var category: Category,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditingFields() {
}
