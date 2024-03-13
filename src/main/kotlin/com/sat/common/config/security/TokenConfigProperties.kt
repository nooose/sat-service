package com.sat.common.config.security

import org.springframework.boot.context.properties.ConfigurationProperties
import kotlin.time.Duration
import kotlin.time.DurationUnit

private const val KEY_MIN_LENGTH = 32

@ConfigurationProperties(prefix = "jwt")
data class TokenConfigProperties(
    val secretKey: String,
    val expirationTime: Duration,
) {
    init {
        require(secretKey.length >= KEY_MIN_LENGTH) { "$KEY_MIN_LENGTH 길이 이상의 키가 필요합니다." }
    }

    val expirationMills = expirationTime.toLong(DurationUnit.MILLISECONDS)
}
