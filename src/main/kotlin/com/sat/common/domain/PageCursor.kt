package com.sat.common.domain

data class PageCursor<T>(
    val nextCursor: CursorRequest,
    val data: T,
)
