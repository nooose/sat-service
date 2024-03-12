package com.sat.board.domain

import jakarta.persistence.*

@Entity
data class Comment(
    @ManyToOne @JoinColumn(name = "comment_id")
    val article: Article,
    val content: String,
    val parentId: Long? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)
