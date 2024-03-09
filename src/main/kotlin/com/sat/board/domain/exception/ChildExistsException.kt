package com.sat.board.domain.exception

import com.sat.common.domain.exception.NoStackTraceException

open class ChildExistsException(
    message: String,
    cause: Throwable? = null,
) : NoStackTraceException(message, cause) {
    override val message: String
        get() = super.message!!
}
