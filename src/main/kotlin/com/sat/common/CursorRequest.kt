package com.sat.common

private const val MAX_SIZE = 30
private const val DEFAULT_SIZE = 15

data class CursorRequest(
    val id: Long?,
    val size: Int = DEFAULT_SIZE,
) {
    init {
        require(size <= MAX_SIZE) { "요청 사이즈 크기는 $MAX_SIZE 초과할 수 없습니다." }
    }

    fun <T : CursorItem> nextFrom(items: List<T>): PageCursor<List<T>> {
        return PageCursor(copy(id = items.nextCursorIdItem, size = size), items)
    }

    companion object {
        fun default(): CursorRequest {
            return CursorRequest(null, 15)
        }
    }
}
