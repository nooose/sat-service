package com.sat.board.command.domain.article

import com.sat.common.domain.exception.NoStackTraceException

open class ChildExistsException(
    message: String,
    cause: Throwable? = null,
) : NoStackTraceException(message, cause) {
    override val message: String
        get() = super.message!!
}
