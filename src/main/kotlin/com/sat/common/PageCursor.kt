package com.sat.common

data class PageCursor<T>(
    val nextCursor: CursorRequest,
    val data: T,
)

interface Cursor {
    val id: Long
}

val <T : Cursor> List<T>.nextCursorId: Long
    get() = if (this.isEmpty()) 0 else this.minOf { it.id }
