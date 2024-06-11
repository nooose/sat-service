package com.sat.common.domain

private const val MAX_SIZE = 30
private const val DEFAULT_SIZE = 15

data class CursorRequest(
    val id: Long?,
    val size: Int = DEFAULT_SIZE,
) {
    init {
        require(size <= MAX_SIZE) { "요청 사이즈 크기는 $MAX_SIZE 초과할 수 없습니다." }
    }

    fun next(minId: Long?): CursorRequest {
        return copy(id = minId, size = size)
    }

    companion object {
        fun default(): CursorRequest {
            return CursorRequest(null, 15)
        }
    }
}
