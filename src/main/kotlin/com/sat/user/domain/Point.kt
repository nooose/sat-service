package com.sat.user.domain

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@Entity
class Point(
    val memberId: Long,
    @Enumerated(EnumType.STRING)
    val type: PointType,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    val point = type.score

    @Column(updatable = false)
    @CreatedDate
    var createdDateTime: LocalDateTime? = null

    companion object {
        fun login(memberId: Long): Point {
            return Point(memberId, PointType.LOGIN)
        }

        fun comment(memberId: Long): Point {
            return Point(memberId, PointType.COMMENT)
        }

        fun article(memberId: Long): Point {
            return Point(memberId, PointType.ARTICLE)
        }
    }
}
