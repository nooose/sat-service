package com.sat.common.exception

open class UnauthorizedException(
    message: String,
    cause: Throwable? = null,
) : NoStackTraceException(message, cause) {
    override val message: String
        get() = super.message!!
}
