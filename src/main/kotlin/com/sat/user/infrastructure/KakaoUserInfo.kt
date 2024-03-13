package com.sat.user.infrastructure

import com.sat.user.domain.port.UserInfo

data class KakaoUserInfo(
    val sub: String,
    val name: String,
    val nickname: String,
    val picture: String,
    val email: String,
    val emailVerified: Boolean,
    val gender: String,
    val birthdate: String,
    val phoneNumber: String,
    val phoneNumberVerified: Boolean
) : UserInfo {
    override fun id(): String {
        return sub
    }

    override fun name(): String {
        return name
    }

    override fun email(): String {
        return email
    }
}
