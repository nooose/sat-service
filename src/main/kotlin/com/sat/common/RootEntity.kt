package com.sat.common

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class RootEntity<T : AbstractAggregateRoot<T>>(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : AbstractAggregateRoot<T>() {

    @Column(updatable = false)
    @CreatedDate
    var createdDateTime: LocalDateTime? = null
        protected set

    @Column(updatable = false)
    @CreatedBy
    var createdBy: Long? = null
        protected set

    @LastModifiedDate
    var modifiedDateTime: LocalDateTime? = null
        protected set

    @LastModifiedBy
    var modifiedBy: Long? = null
        protected set

    fun isOwner(ownerId: Long): Boolean {
        return createdBy == ownerId
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as RootEntity<*>
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
