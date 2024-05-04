package com.sat.user.domain

enum class PointType(
    val score: Int,
    val title: String,
) {
    LOGIN(10, "로그인 적립"),
    ARTICLE(10, "게시글 생성"),
    COMMENT(3, "댓글 작성"),
    HART(3, "좋아요"),
}
