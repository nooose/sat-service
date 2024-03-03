package com.sat.common.domain.exception

abstract class NoStackTraceException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    override fun fillInStackTrace(): Throwable {
        return this
    }
}
