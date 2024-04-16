package com.sat.common.domain

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class AuditingFields {
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
}
