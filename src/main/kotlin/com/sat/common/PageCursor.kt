package com.sat.common

data class PageCursor<T>(
    val nextCursor: CursorRequest,
    val data: T,
)

interface CursorItem {
    val id: Long
}

val <T : CursorItem> List<T>.nextCursorIdItem: Long
    get() = if (this.isEmpty()) 0 else this.minOf { it.id }
