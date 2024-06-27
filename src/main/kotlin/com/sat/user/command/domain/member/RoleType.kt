package com.sat.user.command.domain.member

enum class RoleType(
    val title: String
) {
    BASIC("일반 사용자"),
    ADMIN("관리자"),
    ;

    companion object {
        val DEFAULT = BASIC
    }
}
