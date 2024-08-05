package com.sat.common.domain

enum class RedisCacheName(
    val key: String,
) {
    POINT_RANKING("point-ranking"),
    ARTICLE_RANKING("article-ranking"),
}
