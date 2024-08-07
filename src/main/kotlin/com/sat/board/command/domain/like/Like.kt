package com.sat.board.command.domain.like

import com.sat.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "LIKES")
@Entity
class Like(
    @Column(nullable = false)
    val articleId: Long,
    id: Long = 0L,
) : BaseEntity(id)
