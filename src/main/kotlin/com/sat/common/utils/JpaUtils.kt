package com.sat.common.utils

import com.sat.common.domain.exception.NotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

inline fun <T> JpaRepository<T, Long>.findByIdOrThrow(id: Long, lazyMessage: () -> String): T {
    return findByIdOrNull(id) ?: throw NotFoundException(lazyMessage())
}

