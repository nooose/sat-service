package com.sat.board.command.domain.like

import com.sat.common.BaseEntity
import jakarta.persistence.*

@Table(name = "LIKES")
@Entity
class Like(
    val articleId: Long,
    id: Long = 0L,
) : BaseEntity(id)
