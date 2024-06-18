package com.sat.user.command.domain.member

interface OAuthClient {
    fun userInfo(token: String): UserInfo

    fun supports(provider: String): Boolean
}

interface UserInfo {
    fun id(): String
    fun name(): String
    fun email(): String
}
