package com.sat.common.exception

open class DuplicateException(
    message: String,
    cause: Throwable? = null,
) : NoStackTraceException(message, cause) {
    override val message: String
        get() = super.message!!
}
